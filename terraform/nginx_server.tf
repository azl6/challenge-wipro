resource "aws_instance" "NginxServer" {
  ami             = "ami-00a16e018e54305c6"
  instance_type   = "t2.micro"
  key_name        = aws_key_pair.WiproKeyPair.key_name
  security_groups = [aws_security_group.SG_Proxy.name]

  tags = {
    "Name" = "Nginx-Server"
  }

  provisioner "local-exec" {
    command = <<-EOT
    echo 'Começou o local-exec...'
    ssh -o StrictHostKeyChecking=no ec2-user@${aws_instance.NginxServer.public_ip} bash -s < ./scripts/ConfigureMainProxyServer.sh
    EOT
  }

}