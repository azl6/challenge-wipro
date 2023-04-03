resource "aws_route53_record" "DNS_Backend" {
  zone_id = "Z0468580OC2IH6IG3DO8"
  name    = "backend.wipro.alexthedeveloper.com.br"
  type    = "A"
  ttl     = 300
  records = [aws_instance.BackendServer.public_ip]
}