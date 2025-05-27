pipeline {
    agent any
    stages {
        stage('Checkout') {
            steps {
                git url: 'https://github.com/AndriiVKR/Parabank-Playwright.git', branch: 'main'
            }
        }
        stage('Build Docker Image') {
            steps {
                script {
                    docker.build('parabank-playwright:latest')
                }
            }
        }
        stage('Run Playwright Tests') {
            steps {
                script {
                    docker.image('parabank-playwright:latest').run(
                        '--network parabank-network -v ${WORKSPACE}/test-artifacts:/app/test-artifacts',
                        'mvn test'
                    )
                }
            }
        }
        stage('Publish Test Results') {
            steps {
                junit '**/target/surefire-reports/*.xml'
            }
        }
    }
    post {
        always {
            archiveArtifacts artifacts: 'test-artifacts/screenshots/*.png, test-artifacts/traces/*.zip', allowEmptyArchive: true
        }
    }
}