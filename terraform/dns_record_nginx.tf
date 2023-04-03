resource "aws_route53_record" "DNS_Proxy" {
  zone_id = "Z0468580OC2IH6IG3DO8"
  name    = "proxy.wipro.alexthedeveloper.com.br"
  type    = "A"
  ttl     = 300
  records = [aws_instance.NginxServer.public_ip]
}