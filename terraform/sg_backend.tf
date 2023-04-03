resource "aws_security_group" "SG_Backend" {
  name        = "SG_Backend_Wipro"
  description = "Allows traffic only from Proxy"

  ingress {
    from_port   = 0
    to_port     = 0
    protocol    = "all"
    cidr_blocks = ["${aws_instance.NginxServer.public_ip}/32", "${aws_instance.JenkinsServer.public_ip}/32", "186.216.72.206/32", "143.55.64.0/20", "140.82.112.0/20", "185.199.108.0/22", "192.30.252.0/22"]
    ipv6_cidr_blocks = ["2606:50c0::/32", "2a0a:a440::/29"]
  }

  ingress {
    from_port   = 80
    to_port     = 80
    protocol    = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
  }

  ingress {
    from_port   = 8080
    to_port     = 8080
    protocol    = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
  }

    ingress {
    from_port   = 443
    to_port     = 443
    protocol    = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
  }

  egress {
    from_port   = 0
    to_port     = 0
    protocol    = "all"
    cidr_blocks = ["0.0.0.0/0"]
  }
}