#!/bin/bash

echo '**********INSTALANDO DOCKER**********'

sudo amazon-linux-extras install -y docker

sudo service docker start

sudo chmod a+rwx /var/run/docker.sock

echo '**********SUBINDO CONTEINER DO JENKINS**********'

mkdir /tmp/jenkins_home

docker run --name jenkins -v /tmp/jenkins_home:/var/jenkins_home -v /var/run/docker.sock:/var/run/docker.sock -d -p 8080:8080 jenkins/jenkins 

sleep 5

echo '**********INSTALANDO DOCKER NO JENKINS**********'

docker exec -u 0 jenkins bash -c "curl https://get.docker.com/ > dockerinstall && chmod 777 dockerinstall && ./dockerinstall"

docker exec -u 0 jenkins bash -c "mkdir -p /home/jenkins/bin/ && chown -R 1000:1000 /home/jenkins"

echo '**********INSTALANDO MAVEN NO JENKINS**********'

docker exec -u 0 jenkins bash -c "apt update && apt install -y maven"

echo '**********INSTALANDO VIM NO JENKINS**********'

docker exec -u 0 jenkins bash -c "apt install -y vim"

cat /tmp/jenkins_home/secrets/initialAdminPassword >> /tmp/JENKINS_PASSWORD

echo '**********INSTALANDO NGINX**********'

sudo amazon-linux-extras enable nginx1

sudo yum clean metadata

sudo yum -y install nginx

sudo service nginx start