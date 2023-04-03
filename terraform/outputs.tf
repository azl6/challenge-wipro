output "NginxJenkinsServerIP" {
  value = aws_instance.NginxJenkinsServer.public_ip
}

output "BackendServerIP" {
  value = aws_instance.BackendServer.public_ip
}