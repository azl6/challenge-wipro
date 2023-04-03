resource "aws_key_pair" "WiproKeyPair" {
  key_name   = "WiproKey"
  public_key = file("/home/azl6/.ssh/id_rsa.pub")
}