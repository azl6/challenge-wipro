![wiprologo](https://user-images.githubusercontent.com/80921933/229383449-ad50d599-8ac8-4ebc-a446-edebe81e1f00.jpeg)

# Tecnologias usadas:

Principais:

- Java
- Spring Boot
- JUnit 5
- Cucumber/Gherkin
- TestRestTemplate
- Maven

Secundárias:

- ModelMapper
- NGINX
- Terraform
- Jenkins 
- Docker
- AWS EC2
- AWS Route 53

# Documentação Swagger

O projeto encontra-se devidamente documentado em http://backend.wipro.alexthedeveloper.com.br/swagger.

# Testes 

O projeto conta com 20 testes, sendo 16 testes unitários e 4 testes de integração.

![image](https://user-images.githubusercontent.com/80921933/229383938-53b2438c-74af-4fdb-978f-a29d4d364c7e.png)

O plugin do **Jacoco** apresenta uma cobertura de 90% das linhas do código pelos testes unitários:

![image](https://user-images.githubusercontent.com/80921933/229384064-50380c46-786a-4059-88bd-3ed4e5f105c7.png)

# Testes unitários

Os testes unitários foram feitos com o JUnit5, Mockito, Hamcrest e demais libraries de assertions.

# Testes de integração 

Os testes de integração foram feitos com Cucumber, Gherkin e TestRestTemplate. 

Testei os 4 seguintes cenários:

```gherkin
Feature: Integration tests

  Scenario: User passes a valid CEP and gets a valid address back
    Given a valid CEP
      | numeroCep |
      | 01001-000 |
    When the user sends the valid request
    Then a valid address is returned

  Scenario: User passes a blank CEP and gets MethodArgumentNotValidException
    Given a blank CEP
    When the user sends the request with the blank CEP
    Then he gets MethodArgumentNotValidExcepion with Regex and Blank errors

  Scenario: User passes a null CEP and gets MethodArgumentNotValidException
    Given a null CEP
    When the user sends the request with the null CEP
    Then he gets MethodArgumentNotValidExcepion with Null and Blank errors

  Scenario: User passes a CEP out of the XXXXXXXX or XXXXX-XXX format
    Given a CEP out of the valid formats
      | numeroCep |
      | 0-1-0-0-1-0-0-0 |
    When the user sends a request with the CEP of invalid format
    Then he gets MethodArgumentNotValidExcepion with Regex errors
```

A implementação dos cenários se encontra no arquivo **CepSteps.java**

# Deploy da infraestrutura

Dentro da pasta **terraform**, encontram-se arquivos usados para o deploy da infraestrutura do projeto.

Dentre os recursos, estão: 

- 1 servidor na AWS, do tamanho t2.micro, para servir como **host do Jenkins/NGINX**.
- 1 servidor na AWS, do tamanho t2.micro, para servir como **host da API em Spring**.
- 1 hosted-zone com o domínio de `alexthedeveloper.com.br`.
- 1 DNS record, `proxy.wipro.alexthedeveloper.com.br`, do tipo A, apontando para o IPv4 público do servidor Jenkins/NGINX.
- 1 DNS record, `backend.wipro.alexthedeveloper.com.br`, do tipo A, apontando para o IPv4 público do servidor da API em Spring.
- 1 SSH key-pair, para que eu possa logar nos servidores via SSH (e também para uso do Jenkins).

# CI/CD

Um contêiner do Jenkins foi deployado via IaC com o Terraform. Utilizei-o para a construção da seguinte pipeline de CI/CD:

```groovy
pipeline {
    agent any 

    environment {
      DOCKER_USR=credentials('docker_usr')
      DOCKER_PASSWD=credentials('docker_passwd')
    }

    stages {
        stage('Test') { 
            steps {
                sh """
                echo 'Testing...'
                mvn test
                """ 
            }
        }

        stage('Package') { 
            steps {
                sh """
                echo 'Generating project package...'
                mvn clean package -Dmaven.test.skip=true
                """ 
            }
        }

        stage('Build') { 
            steps {
                sh """
                    echo 'Building...'
                    docker build -t azold6/wipro-backend:$BUILD_NUMBER . 
                """ 
            }
        }

        stage('Push') { 
            steps {
                sh """
                    echo 'Pushing...'
                    docker login -u="$DOCKER_USR" -p="$DOCKER_PASSWD"
                    docker push -t azold6/wipro-backend:$BUILD_NUMBER . 
                """ 
            }
        }

        stage('Deploy') { 
            steps {
                sh """
                    ssh ec2-user@backend.wipro.alexthedeveloper.com.br "docker stop wipro-backend"
                    ssh ec2-user@backend.wipro.alexthedeveloper.com.br "docker run -p 8181:8181 -d --name wipro-backend --rm azold6/wipro-backend:$BUILD_NUMBER"
                """                
            }
        }
    }
}
```

Dentro do Jenkins, criei um projeto de **Pipeline** que usa Webhooks como trigger:

![image](https://user-images.githubusercontent.com/80921933/229385388-e344845c-899b-48ee-baca-f02bb6543ea4.png)

Assim que eu fizer qualquer push para o repositório, os Webhooks enviarão um request para o servidor que hospeda o Jenkins, iniciando o processo de entrega contínua, que testará, buildará e pushará a nova imagem Docker gerada para o Dockerhub, para depois deployar a nova versão da aplicação no servidor da API.
