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
                        -Dsonar.projectKey=etudedecas \
                        -Dsonar.organization=etudedecas \
                        -Dsonar.host.url=https://sonarcloud.io \
                        -Dsonar.login=8b5348f260ef12a9072018111b329a68734ec22a \
                        -Dsonar.qualitygate.wait=false \
                        -Dsonar.coverage.exclusions=**/* \
                        -Dsonar.cpd.exclusions=**/*
                    '''
                }
            }
        }
    }

    post {
        always {
            cleanWs()
        }
        success {
            echo 'Pipeline completed successfully!'
        }
        failure {
            echo 'Pipeline failed!'
        }
    }
}