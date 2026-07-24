pipeline {
    agent any

    stages {

        stage('Checkout Code') {
            steps {
                git branch: 'main',
                    credentialsId: 'github-creds',
                    url: 'https://github.com/vinay-kamutam/springboot-cicd-demo.git'
            }
        }
        stage('Build') {
            steps {
                sh 'mvn clean package'
            }
        }

    }
}
