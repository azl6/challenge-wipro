#!/bin/bash

echo '**********INSTALANDO DOCKER**********'

sudo amazon-linux-extras install -y docker

sudo service docker start

sudo chmod a+rwx /var/run/docker.sock