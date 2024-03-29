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
            steps {
                script{
                      docker.withRegistry('https://216413260795.dkr.ecr.us-east-1.amazonaws.com', 'ecr:us-east-1:aws-credentials') {
                        withAWS(region: "${AWS_ECR_REGION}", credentials: 'aws-credentials') {
                        	
                            sh("aws ecs register-task-definition --region ${AWS_ECR_REGION} --family ${AWS_ECS_TASK_DEFINITION} --execution-role-arn arn:aws:iam::216413260795:role/ecsTaskExecutionRole --requires-compatibilities EC2 --network-mode bridge --cpu 1024 --memory 512 --container-definitions file://${AWS_ECS_TASK_DEFINITION_PATH}")
                            def taskRevision = sh(script: "aws ecs describe-task-definition --task-definition ${AWS_ECS_TASK_DEFINITION} | egrep \"revision\" | tr \"/\" \" \" | awk '{print \$2}' | sed 's/,\$//'", returnStdout: true)
                            sh("aws ecs update-service --cluster ${AWS_ECS_CLUSTER} --service ${AWS_ECS_SERVICE} --task-definition ${AWS_ECS_TASK_DEFINITION}:${taskRevision}")
                       }
                    }
                }
                }
            }
       
    }
}