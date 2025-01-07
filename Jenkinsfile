pipeline {
    agent any
    
    environment {
        BLOG_PORT = '3000'
    }
    
    tools {
        maven 'maven'
        allure 'Allure'
    }

    stages {
        stage('Cleanup Workspace') {
            steps {
                cleanWs()
                checkout scm
            }
        }
        
        stage('Install Dependencies') {
            steps {
                script {
                    try {
                        sh '''
                            mvn clean install -DskipTests \
                                -Dorg.slf4j.simpleLogger.log.org.apache.maven.cli.transfer.Slf4jMavenTransferListener=warn \
                                -B -V
                        '''
                    } catch (Exception e) {
                        echo "Bağımlılık yükleme hatası: ${e.message}"
                        currentBuild.result = 'UNSTABLE'
                        throw e
                    }
                }
            }
        }
        
        stage('Start Blog Server') {
            steps {
                script {
                    try {
                        // Önceki process'i temizle
                        sh '''
                            lsof -ti:${BLOG_PORT} | xargs kill -9 || true
                        '''
                        
                        // Blog sunucusunu Python ile başlat
                        sh '''
                            cd blog
                            python3 -m http.server ${BLOG_PORT} &
                            echo $! > .blog-server.pid
                            sleep 5
                        '''
                    } catch (Exception e) {
                        echo "Blog sunucusu başlatma hatası: ${e.message}"
                        currentBuild.result = 'UNSTABLE'
                        throw e
                    }
                }
            }
        }
        
        stage('Run Tests') {
            steps {
                script {
                    try {
                        sh '''
                            mvn test \
                                -Dorg.slf4j.simpleLogger.log.org.apache.maven.cli.transfer.Slf4jMavenTransferListener=warn \
                                -B -V
                        '''
                    } catch (Exception e) {
                        echo "Test çalıştırma hatası: ${e.message}"
                        currentBuild.result = 'UNSTABLE'
                        throw e
                    }
                }
            }
        }
        
        stage('Generate Reports') {
            steps {
                script {
                    try {
                        // Process'leri temizle
                        sh '''
                            if [ -f blog/.blog-server.pid ]; then
                                kill -9 $(cat blog/.blog-server.pid) || true
                                rm blog/.blog-server.pid
                            fi
                        '''
                        
                        // Allure raporu oluştur
                        allure([
                            includeProperties: false,
                            jdk: '',
                            properties: [],
                            reportBuildPolicy: 'ALWAYS',
                            results: [[path: 'target/allure-results']]
                        ])
                        
                    } catch (Exception e) {
                        echo "Rapor oluşturma hatası: ${e.message}"
                        currentBuild.result = 'UNSTABLE'
                        throw e
                    }
                }
            }
        }
    }
    
    post {
        always {
            script {
                // Test sonuçlarını topla
                def testResults = []
                def allureResults = []
                
                // JUnit test sonuçlarını oku
                def junitResults = junit allowEmptyResults: true, testResults: '**/target/surefire-reports/*.xml'
                testResults << [
                    name: 'JUnit',
                    total: junitResults.totalCount,
                    failed: junitResults.failCount,
                    skipped: junitResults.skipCount,
                    passed: junitResults.passCount
                ]
                
                // Allure raporlarını arşivle
                archiveArtifacts allowEmptyArchive: true, artifacts: 'target/allure-results/**/*.*'
                
                // Test sonuç özetini oluştur
                def summary = """
╔═══════════════════════════╗
║   Test Sonuç Özeti        ║
╚═══════════════════════════╝

📊 Raporlar:
"""
                testResults.each { result ->
                    summary += """
🔍 ${result.name} Sonuçları:
   ✅ Başarılı: ${result.passed}
   ❌ Başarısız: ${result.failed}
   ⏭️ Atlanan: ${result.skipped}
   📝 Toplam: ${result.total}
"""
                }
                
                // Sonuçları ekrana yazdır
                echo summary
                
                // Workspace'i temizle
                cleanWs()
            }
        }
    }
}
