pipeline {
    agent any
    
    environment {
        BLOG_PORT = '3000'
    }
    
    tools {
        maven 'Maven'
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
                sh 'mvn clean install -DskipTests'
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
                        
                        // Blog sunucusunu başlat
                        sh '''
                            cd blog
                            npm install
                            npm start &
                            echo "Blog sunucusu başlatıldı"
                        '''
                        
                        // Sunucunun başlamasını bekle
                        sleep 10
                    } catch (Exception e) {
                        echo "Blog sunucusu başlatılamadı: ${e.message}"
                        currentBuild.result = 'UNSTABLE'
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
                                -Dtest.env=jenkins \
                                -Dwebdriver.chrome.whitelistedIps="" \
                                -Dwebdriver.chrome.verboseLogging=true \
                                -Dcucumber.plugin="pretty,json:target/cucumber-reports/cucumber.json,html:target/cucumber-reports/cucumber.html,io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm"
                        '''
                    } catch (Exception e) {
                        echo "Test çalıştırma hatası: ${e.message}"
                        currentBuild.result = 'UNSTABLE'
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
                            pkill -f allure || true
                            pkill -f jetty || true
                            pkill -f chrome || true
                            pkill -f chromedriver || true
                        '''
                        
                        // Allure raporu oluştur
                        allure([
                            includeProperties: false,
                            jdk: '',
                            properties: [],
                            reportBuildPolicy: 'ALWAYS',
                            results: [[path: 'target/allure-results']]
                        ])
                        
                        // Cucumber raporunu zip'le
                        sh '''
                            cd target/cucumber-reports
                            zip -r ../cucumber-reports.zip ./*
                        '''
                        
                        // Allure raporunu zip'le
                        sh '''
                            cd ${WORKSPACE}/allure-report
                            zip -r ../allure-report.zip ./*
                        '''
                        
                        // Artifact'leri arşivle
                        archiveArtifacts(
                            artifacts: '''
                                target/cucumber-reports.zip,
                                allure-report.zip,
                                target/allure-results/**/*
                            ''',
                            fingerprint: true,
                            allowEmptyArchive: true
                        )
                    } catch (Exception e) {
                        echo "Rapor oluşturma hatası: ${e.message}"
                        currentBuild.result = 'UNSTABLE'
                    }
                }
            }
        }
    }
    
    post {
        always {
            script {
                try {
                    // Process'leri temizle
                    sh '''
                        pkill -f allure || true
                        pkill -f jetty || true
                        pkill -f chrome || true
                        pkill -f chromedriver || true
                        lsof -ti:${BLOG_PORT} | xargs kill -9 || true
                    '''
                } catch (Exception e) {
                    echo "Process temizleme hatası: ${e.message}"
                } finally {
                    // Workspace'i temizle
                    cleanWs(
                        cleanWhenNotBuilt: false,
                        deleteDirs: true,
                        disableDeferredWipeout: true,
                        notFailBuild: true
                    )
                }
                
                // Test sonuç özeti
                echo """
╔═══════════════════════════╗
║   Test Sonuç Özeti        ║
╚═══════════════════════════╝

📊 Raporlar:
- 📈 Allure: ${BUILD_URL}allure
- 📁 Artifacts: ${BUILD_URL}artifact

🎯 Build Durumu: ${currentBuild.result ?: 'SUCCESS'}
⏱️ Süre: ${currentBuild.durationString}
                """
            }
        }
        
        failure {
            script {
                echo """
❌ Build başarısız oldu!
- Build URL: ${BUILD_URL}
- Console Log: ${BUILD_URL}console
                """
            }
        }
        
        unstable {
            script {
                echo """
⚠️ Build kararsız durumda!
- Build URL: ${BUILD_URL}
- Test Reports: ${BUILD_URL}allure
                """
            }
        }
    }
}
