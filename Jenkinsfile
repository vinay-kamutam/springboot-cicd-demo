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

        stage('Deploy to Application Server') {
            steps {
                sshagent(credentials: ['ec2-ssh']) {
                    sh '''
ssh -o StrictHostKeyChecking=no ubuntu@100.30.173.217 <<EOF
docker pull vinaykamutam/springboot-cicd-demo:v1
docker stop springboot-app || true
docker rm springboot-app || true
docker run -d \
  --name springboot-app \
  -p 8080:8080 \
  vinaykamutam/springboot-cicd-demo:v1
EOF
'''
                }
            }
        }
    }
}
