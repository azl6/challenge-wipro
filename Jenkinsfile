pipeline {
    agent any 

    environment {
      DOCKER_USR=credentials('docker_usr')
      DOCKER_PASSWD=credentials('docker_passwd')
    }

    stages {
        stage('Test') { 
            steps {
                sh """
                echo 'Testing...'
                mvn test
                """ 
            }
        }

        stage('Package') { 
            steps {
                sh """
                echo 'Generating project package...'
                mvn clean package -Dmaven.test.skip=true
                """ 
            }
        }

        stage('Build') { 
            steps {
                sh """
                    echo 'Building...'
                    docker build -t azold6/wipro-backend:$BUILD_NUMBER . 
                """ 
            }
        }

        stage('Push') { 
            steps {
                sh """
                    echo 'Pushing...'
                    docker login -u="$DOCKER_USR" -p="$DOCKER_PASSWD"
                    docker push azold6/wipro-backend:$BUILD_NUMBER 
                """ 
            }
        }

        stage('Deploy') { 
            steps {
                sh """
                    ssh -o StrictHostKeyChecking=no ec2-user@backend.wipro.alexthedeveloper.com.br "docker stop wipro-backend"
                    ssh -o StrictHostKeyChecking=no ec2-user@backend.wipro.alexthedeveloper.com.br "docker run -p 80:80 -d --name wipro-backend --rm azold6/wipro-backend:$BUILD_NUMBER"
                """                
            }
        }
    }
}