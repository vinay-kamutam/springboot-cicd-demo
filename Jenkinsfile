pipeline {
    agent any

    stages {

        stage('Build') {
            steps {
                sh 'mvn clean package'
            }
        }

        stage('SonarQube Analysis') {
            environment {
                SCANNER_HOME = tool 'SonarScanner'
            }
            steps {
                withSonarQubeEnv('SonarQube') {
                    sh """
                        ${SCANNER_HOME}/bin/sonar-scanner \
                          -Dsonar.projectKey=springboot-cicd-demo \
                          -Dsonar.projectName="Spring Boot CI/CD Demo" \
                          -Dsonar.sources=src \
                          -Dsonar.java.binaries=target/classes
                        """
                    }
                }
            }

        stage('Build Docker Image') {
            steps {
                sh "docker build -t vinaykamutam/springboot-cicd-demo:${BUILD_NUMBER} ."
            }
        }

        stage('Trivy Security Scan') {
            steps {
                sh """
                trivy image \
                --severity HIGH,CRITICAL \
                --format table \
                --output trivy-report.txt \
                --exit-code 0 \
                vinaykamutam/springboot-cicd-demo:${BUILD_NUMBER}
                """
            }
        }

        stage('Push Docker Image') {
            steps {
                withDockerRegistry([credentialsId: 'vinaykamutam', url: '']) {
                    sh "docker push vinaykamutam/springboot-cicd-demo:${BUILD_NUMBER}"
                }
            }
        }

        stage('Deploy to Application Server') {
            steps {
                sshagent(credentials: ['ec2-ssh']) {
                    sh '''
ssh -o StrictHostKeyChecking=no ubuntu@172.31.45.197 <<EOF
docker pull vinaykamutam/springboot-cicd-demo:${BUILD_NUMBER}

docker stop springboot-app || true
docker rm springboot-app || true

docker run -d \
  --name springboot-app \
  --restart unless-stopped \
  -p 8080:8080 \
  vinaykamutam/springboot-cicd-demo:${BUILD_NUMBER}
EOF
'''
                }
            }
        }
    }

    post {
        always {
            archiveArtifacts artifacts: 'trivy-report.txt', fingerprint: true
        }
    }
}
