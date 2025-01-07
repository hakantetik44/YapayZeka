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
                        export PATH=$JAVA_HOME/bin:$M2_HOME/bin:$PATH

                        # Create directories
                        mkdir -p ${EXCEL_REPORTS} ${ALLURE_RESULTS} target/screenshots

                        # Print environment info
                        echo "JAVA_HOME: $JAVA_HOME"
                        echo "M2_HOME: $M2_HOME"
                        echo "PATH: $PATH"

                        # Verify Java and Maven
                        echo "Java version:"
                        java -version

                        echo "Maven version:"
                        mvn -v
                    '''
                }
            }
        }

        stage('Construction') {
            steps {
                script {
                    try {
                        echo " Bağımlılıklar yükleniyor..."
                        sh '''#!/bin/bash
                            export JAVA_HOME=/Users/hakantetik/Library/Java/JavaVirtualMachines/corretto-17.0.13/Contents/Home
                            export M2_HOME=/usr/local/Cellar/maven/3.9.9/libexec
                            export PATH=$JAVA_HOME/bin:$M2_HOME/bin:$PATH

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
                        echo " Testler başlatılıyor..."
                        sh """#!/bin/bash
                            export JAVA_HOME=/Users/hakantetik/Library/Java/JavaVirtualMachines/corretto-17.0.13/Contents/Home
                            export M2_HOME=/usr/local/Cellar/maven/3.9.9/libexec
                            export PATH=\$JAVA_HOME/bin:\$M2_HOME/bin:\$PATH

                            mvn test -Dtest=runners.TestRunner \\
                                -Dbrowser=${params.BROWSER} \\
                                -Dcucumber.plugin="pretty,json:target/cucumber.json,io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm"
                        """
                    } catch (Exception e) {
                        currentBuild.result = 'FAILURE'
                        throw e
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
                echo """╔═══════════════════════════╗
║   Test Sonuç Özeti        ║
╚═══════════════════════════╝

 Raporlar:
- Allure: ${BUILD_URL}allure/
- Excel: ${BUILD_URL}artifact/${EXCEL_REPORTS}/
- Cucumber: ${BUILD_URL}artifact/target/cucumber-reports/

Tarayıcı: ${params.BROWSER}
${currentBuild.result == 'SUCCESS' ? ' BAŞARILI' : ' BAŞARISIZ'}"""
            }
            cleanWs notFailBuild: true
        }
    }
}
