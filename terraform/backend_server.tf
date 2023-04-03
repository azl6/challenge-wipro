resource "aws_instance" "BackendServer" {
  ami           = "ami-00a16e018e54305c6"
  instance_type = "t2.micro"
  key_name      = aws_key_pair.WiproKeyPair.key_name
  security_groups = [ aws_security_group.SG_Backend.name ]

  tags = {
    "Name" = "Backend-Server"
  }

  connection {
    type        = "ssh"
    user        = "ec2-user"
    host        = self.public_ip
    private_key = file("/home/azl6/.ssh/id_rsa")
  }

  provisioner "local-exec" {
    command = "ssh ec2-user@${self.public_ip} bash -s < ./scripts/InstallDocker.sh"
  }



}