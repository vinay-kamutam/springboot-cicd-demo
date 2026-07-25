pipeline {
    agent any

    stages {

        stage('Build') {
            steps {
                sh 'mvn clean package'
            }
        }

        stage('Build Docker Image') {
            steps {
                sh 'docker build -t vinaykamutam/springboot-cicd-demo:v1 .'
            }
        }
        stage('Push Docker Image') {
            steps {
                withDockerRegistry([credentialsId: 'vinaykamutam', url: '']) {
                    sh 'docker push vinaykamutam/springboot-cicd-demo:v1'
                    }
            }
        }
        
    }
}
