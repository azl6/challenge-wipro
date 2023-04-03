resource "aws_security_group" "SG_Backend" {
  name        = "SG_Backend_Wipro"
  description = "Allows traffic only from Proxy"

  ingress {
    from_port   = 0
    to_port     = 0
    protocol    = "all"
    cidr_blocks = ["${aws_instance.NginxServer.public_ip}/32", "${aws_instance.JenkinsServer.public_ip}/32", "186.216.72.206/32"]
  }

  ingress {
    from_port   = 8181
    to_port     = 8181
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