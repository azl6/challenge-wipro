output "NginxServerIP" {
  value = aws_instance.NginxServer.public_ip
}

output "BackendServerIP" {
  value = aws_instance.BackendServer.public_ip
}

output "JenkinsServerIP" {
  value = aws_instance.JenkinsServer.public_ip
}