#!/bin/bash

echo '**********COPYING NEEDED FILES**********'

ssh -i /home/azl6/Projects/challenge-wipro/terraform/ssh_keys/id_rsa ec2-user@$1 "mkdir -p /home/ec2-user/.ssh"

scp -i /home/azl6/Projects/challenge-wipro/terraform/ssh_keys/id_rsa /home/azl6/Projects/challenge-wipro/terraform/ssh_keys/id_rsa ec2-user@$1:/home/ec2-user/.ssh/id_rsa