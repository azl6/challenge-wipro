resource "aws_instance" "WiproServer" {
  ami           = "ami-00a16e018e54305c6"
  instance_type = "t2.micro"
  key_name      = aws_key_pair.WiproServerKeyPair.key_name

  tags = {
    "Name" = "Wipro-Server"
  }

  provisioner "local-exec" {
    command = "ssh -o StrictHostKeyChecking=no ec2-user@${aws_instance.WiproServer.public_ip} bash -s < /home/azl6/Projects/challenge-wipro/terraform/scripts/install_docker_jenkins_nginx.sh"



  }

}