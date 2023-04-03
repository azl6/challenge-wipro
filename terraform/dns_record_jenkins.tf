resource "aws_route53_record" "DNS_Jenkins" {
  zone_id = "Z0468580OC2IH6IG3DO8"
  name    = "jenkins.wipro.alexthedeveloper.com.br"
  type    = "A"
  ttl     = 300
  records = [aws_instance.JenkinsServer.public_ip]
}