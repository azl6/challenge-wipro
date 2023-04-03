resource "aws_security_group" "SG_Backend" {
  name        = "SG_Backend_Wipro"
  description = "Allows traffic only from Proxy"

  ingress {
    from_port   = 0
    to_port     = 0
    protocol    = "all"
    cidr_blocks = ["${aws_instance.NginxJenkinsServer.public_ip}/32", "186.216.72.206/32" ]
  }

  egress {
    from_port   = 0
    to_port     = 0
    protocol    = "all"
    cidr_blocks = ["0.0.0.0/0"]
  }
}