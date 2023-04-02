#!/bin/bash

echo '**********INSTALANDO DOCKER**********'

sudo amazon-linux-extras install -y docker

sudo service docker start

sudo chmod a+rwx /var/run/docker.sock

echo '**********SUBUNDO CONTEINER DO JENKINS**********'

mkdir /tmp/jenkins_home

docker run --name jenkins -v /tmp/jenkins_home:/var/jenkins_home -d -p 8080:8080 jenkins/jenkins 

docker exec -it -u 0 jenkins bash -c "apt update && apt install -y maven"

cat /tmp/jenkins_home/secrets/initialAdminPassword >> /tmp/JENKINS_PASSWORD

echo '**********INSTALANDO NGINX**********'

sudo amazon-linux-extras enable nginx1

sudo yum clean metadata

sudo yum -y install nginx

sudo service nginx start