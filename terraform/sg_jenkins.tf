resource "aws_security_group" "SG_Jenkins" {
  name = "SG_Jenkins_Wipro"

  ingress {
    from_port   = 0
    to_port     = 0
    protocol    = "all"
    cidr_blocks = ["186.216.72.206/32"]
  }

  egress {
    from_port   = 0
    to_port     = 0
    protocol    = "all"
    cidr_blocks = ["0.0.0.0/0"]
  }
}