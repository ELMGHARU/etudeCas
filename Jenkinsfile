pipeline {
    agent any

    tools {
        maven 'Maven'
        jdk 'JDK17'
    }

    stages {
        stage('Git Clone') {
            steps {
                git branch: 'main',
                    url: 'https://github.com/ELMGHARU/etudeCas.git'
            }
        }

        stage('Build') {
            steps {
                bat 'mvn clean install'
            }
        }

        stage('Build & Analysis') {
            steps {
                withSonarQubeEnv('SonarCloud') {
                    bat '''
                        mvn sonar:sonar \
                        -Dsonar.projectKey=spring-cloud \
                        -Dsonar.organization=emsig5ky \
                        -Dsonar.host.url=https://sonarcloud.io \
                        -Dsonar.login=e8ffa2692c10fb241956a43d6bfb66c09172a282 \
                        -Dsonar.qualitygate.wait=true
                    '''
                }
            }
        }
    }

    post {
        always { cleanWs() }
        success { echo 'Pipeline completed successfully!' }
        failure { echo 'Pipeline failed!' }
    }
}