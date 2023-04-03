resource "aws_instance" "JenkinsServer" {
  ami             = "ami-00a16e018e54305c6"
  instance_type   = "t2.small"
  key_name        = aws_key_pair.WiproKeyPair.key_name
  security_groups = [aws_security_group.SG_Jenkins.name]

  tags = {
    "Name" = "Jenkins-Server"
  }

  provisioner "local-exec" {
    command = <<-EOT
    sleep 5
    echo 'Começou o local-exec...'
    ssh -o StrictHostKeyChecking=no ec2-user@${aws_instance.JenkinsServer.public_ip} bash -s < ./scripts/InstallJenkins.sh
    EOT
  }

}