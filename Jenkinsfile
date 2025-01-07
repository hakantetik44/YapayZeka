pipeline {
    agent any
    
    environment {
        GITHUB_REPO = 'https://github.com/hakantetik44/YapayZeka.git'
        BRANCH = 'main'
    }
    
    stages {
        stage('Checkout') {
            steps {
                git branch: "${BRANCH}", url: "${GITHUB_REPO}"
            }
        }
        
        stage('Test') {
            steps {
                sh '''
                    echo "Running tests..."
                    cd /Users/hakantetik/Desktop/YapayZeka
                    mvn test
                '''
            }
        }
        
        stage('Deploy') {
            steps {
                sh '''
                    echo "Deploying to test environment..."
                    cd /Users/hakantetik/Desktop/YapayZeka/blog_old
                    python3 -m http.server 8000 &
                '''
            }
        }
    }
    
    post {
        success {
            echo 'Pipeline başarıyla tamamlandı!'
        }
        failure {
            echo 'Pipeline başarısız oldu!'
        }
    }
}
