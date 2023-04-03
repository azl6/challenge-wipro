#!/bin/bash

echo '**********INSTALANDO NGINX**********'

sudo amazon-linux-extras enable nginx1

sudo yum clean metadata

sudo yum -y install nginx

sudo service nginx start