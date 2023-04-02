resource "aws_key_pair" "WiproServerKeyPair" {
  key_name   = "JenkinsServerKey"
  public_key = file("/home/azl6/.ssh/id_rsa.pub")
}