pipeline {
    agent any
    options {
        skipStagesAfterUnstable()
    }
    environment {
        AWS_ECS_TASK_DEFINITION = 'mc-taskdefinition'
        AWS_ECS_TASK_DEFINITION_PATH = './ecs/container-definition-update-image.json'
        AWS_ECS_CLUSTER = 'McCluster'
        AWS_ECS_SERVICE = 'McService'
        AWS_ECR_REGION  = 'us-east-1'

    }
    stages {
         stage('Clone repository') { 
            steps { 
                script{
                checkout scm
                }
            }
        }
		stage('Build & Test') {
            steps {
                sh 'mvn -B -DskipTests clean package'
                //sh 'mvn test'
            }
        }
        stage('Docker Build') { 
            steps { 
                script{
                 app = docker.build("mc-package")
                }
            }
        }
        stage('Deploy ECR') {
            steps {
                script{
                    docker.withRegistry('https://216413260795.dkr.ecr.us-east-1.amazonaws.com', 'ecr:us-east-1:aws-credentials') {
                    		app.push("latest")
                    }
                }
            }
        }
        stage('Deploy AWS EC2') {
            
             withAWS(region: 'us-east-1', credentials: 'aws-credentials') {
                     sh 'aws ecs update-service --region ap-northeast-1 --cluster McCluster --service McService --force-new-deployment'
                 }
                
        }
       
    }
}