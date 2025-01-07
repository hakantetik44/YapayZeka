pipeline {
    agent any

    tools {
        maven 'maven'
        jdk 'JDK17'
        allure 'Allure'
    }

    environment {
        // Java ve Maven için kesin yolları kullan
        JAVA_HOME = '/usr/local/Cellar/openjdk@17/17.0.13/libexec/openjdk.jdk/Contents/Home'
        M2_HOME = '/usr/local/Cellar/maven/3.9.9/libexec'
        PATH = "${JAVA_HOME}/bin:${M2_HOME}/bin:${env.PATH}"

        // Proje değişkenleri
        PROJECT_NAME = 'Blog Test Automation'
        TIMESTAMP = new Date().format('yyyy-MM-dd_HH-mm-ss')
        ALLURE_RESULTS = 'target/allure-results'
        EXCEL_REPORTS = 'target/rapports-tests'
        BLOG_PORT = '3000'
    }

    parameters {
        choice(
            name: 'BROWSER',
            choices: ['chrome', 'firefox', 'safari'],
            description: 'Test için kullanılacak tarayıcıyı seçin'
        )
    }

    stages {
        stage('Initialisation') {
            steps {
                script {
                    echo "╔═══════════════════════════════╗\n║ Blog Test Otomasyonu Başlıyor ║\n╚═══════════════════════════════╝"
                    cleanWs()
                    checkout scm

                    // Environment verification
                    sh '''#!/bin/bash
                        echo "=== Environment Verification ==="

                        # Set up environment variables explicitly
                        export JAVA_HOME=/Users/hakantetik/Library/Java/JavaVirtualMachines/corretto-17.0.13/Contents/Home
                        export M2_HOME=/usr/local/Cellar/maven/3.9.9/libexec
                        export PATH=\$JAVA_HOME/bin:\$M2_HOME/bin:\$PATH

                        # Create directories
                        mkdir -p \${EXCEL_REPORTS} \${ALLURE_RESULTS} target/screenshots

                        # Print environment info
                        echo "JAVA_HOME: \$JAVA_HOME"
                        echo "M2_HOME: \$M2_HOME"
                        echo "PATH: \$PATH"

                        # Verify Java and Maven
                        echo "Java version:"
                        java -version

                        echo "Maven version:"
                        mvn -v
                    '''
                }
            }
        }

        stage('Start Blog Server') {
            steps {
                script {
                    try {
                        echo "🌐 Blog sunucusu başlatılıyor (Port: ${BLOG_PORT})..."
                        sh """#!/bin/bash
                            # Eğer port kullanımdaysa, işlemi sonlandır
                            lsof -ti:\${BLOG_PORT} | xargs kill -9 || true
                            
                            # Blog klasörüne git ve sunucuyu başlat
                            cd /Users/hakantetik/Desktop/YapayZeka/blog_old
                            python3 -m http.server \${BLOG_PORT} > /dev/null 2>&1 &
                            
                            # Sunucunun başlamasını bekle
                            sleep 5
                            
                            # Sunucunun çalıştığını kontrol et
                            curl -s http://localhost:\${BLOG_PORT} > /dev/null
                            if [ \$? -eq 0 ]; then
                                echo "Blog sunucusu başarıyla başlatıldı!"
                            else
                                echo "Blog sunucusu başlatılamadı!"
                                exit 1
                            fi
                        """
                    } catch (Exception e) {
                        echo "Blog sunucusu başlatılırken hata oluştu: ${e.message}"
                        currentBuild.result = 'FAILURE'
                        throw e
                    }
                }
            }
        }

        stage('Construction') {
            steps {
                script {
                    try {
                        echo "📦 Bağımlılıklar yükleniyor..."
                        sh '''#!/bin/bash
                            export JAVA_HOME=/Users/hakantetik/Library/Java/JavaVirtualMachines/corretto-17.0.13/Contents/Home
                            export M2_HOME=/usr/local/Cellar/maven/3.9.9/libexec
                            export PATH=\$JAVA_HOME/bin:\$M2_HOME/bin:\$PATH

                            mvn clean install -DskipTests
                        '''
                    } catch (Exception e) {
                        currentBuild.result = 'FAILURE'
                        throw e
                    }
                }
            }
        }

        stage('Test Execution') {
            steps {
                script {
                    try {
                        echo "🧪 Testler başlatılıyor (Headless mod)..."
                        sh """#!/bin/bash
                            # Maven testlerini çalıştır
                            mvn clean test \
                                -Dbrowser=${params.BROWSER} \
                                -DheadlessMode=true \
                                -Dcucumber.filter.tags="not @ignore" \
                                -Dcucumber.execution.exclusive=true \
                                -Dcucumber.plugin="io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm" \
                                -Dtest.env=jenkins
                        """
                    } catch (Exception e) {
                        currentBuild.result = 'UNSTABLE'
                        echo "Test çalıştırma sırasında hata oluştu: ${e.message}"
                    }
                }
            }
        }

        stage('Reports') {
            steps {
                script {
                    try {
                        // Allure Report
                        allure([
                            includeProperties: true,
                            reportBuildPolicy: 'ALWAYS',
                            results: [[path: "${ALLURE_RESULTS}"]]
                        ])

                        // Cucumber Report
                        if (fileExists('target/cucumber-reports')) {
                            sh """
                                cd target
                                zip -q -r cucumber-reports.zip cucumber-reports/
                            """
                        }

                        if (fileExists("${ALLURE_RESULTS}")) {
                            sh """
                                cd target && zip -q -r allure-report.zip allure-results/
                            """
                        }
                    } catch (Exception e) {
                        currentBuild.result = 'UNSTABLE'
                    }
                }
            }
            post {
                always {
                    archiveArtifacts artifacts: "${EXCEL_REPORTS}/**/*.xlsx,target/cucumber-reports.zip,target/allure-report.zip", allowEmptyArchive: true
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
                    
                    // Allure raporu oluştur
                    allure([
                        includeProperties: false,
                        jdk: '',
                        properties: [],
                        reportBuildPolicy: 'ALWAYS',
                        results: [[path: 'target/allure-results']]
                    ])
                    
                    // Allure raporunu zip'le
                    sh '''
                        cd ${WORKSPACE}/allure-report
                        zip -r ${WORKSPACE}/allure-report.zip ./*
                    '''
                    
                    // Artifact'leri arşivle
                    archiveArtifacts(
                        artifacts: '''
                            target/**/*,
                            allure-report.zip
                        ''',
                        fingerprint: true,
                        allowEmptyArchive: true
                    )
                    
                } catch (Exception e) {
                    echo "Allure raporu oluşturulurken hata: ${e.message}"
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
