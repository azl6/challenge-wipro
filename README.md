![venture-thumbnail-1175x312](https://user-images.githubusercontent.com/80921933/229437775-d5d63121-55f4-4546-94b6-ed12ff6c251d.jpg)

# Teste técnico - Wipro

>Status: Concluído ✔️


# Tecnologias usadas:

- Java
- Spring Boot
- jUnit 5
- Mockito
- MockMvc
- Assertions, Hamcrest
- Cucumber/Gherkin
- TestRestTemplate
- Jacoco
- Spring Validation
- Maven
- ModelMapper
- NGINX
- Terraform
- Jenkins 
- Docker
- Shell Script
- SSH
- AWS EC2
- AWS Route 53

# Acessando o projeto

Todo a infraestrutura do projeto foi deployada via Terraform. Sendo assim, o projeto pode ser acessado a partir dos seguintes links:

- Servidor NGINX de proxy-reverso **(HTTPS)**: https://proxy.wipro.alexthedeveloper.com.br
- Servidor Jenkins para CI/CD: http://jenkins.wipro.alexthedeveloper.com.br:8080
- Servidor host da API **(SEM HTTPS)**: http://backend.wipro.alexthedeveloper.com.br

Note que o NGINX foi implementado justamente para servir como um proxy-reverso da aplicação, sendo assim, recomendo o acesso aos endpoints pelo DNS do NGINX:

- Endpoint **/v1/consulta-endereco**: https://proxy.wipro.alexthedeveloper.com.br/v1/consulta-endereco
- Endpoint **/swagger**: https://proxy.wipro.alexthedeveloper.com.br/swagger **(LEIA ABAIXO!)**

Você perceberá a seguinte tela ao acessar o Swagger a partir do NGINX:

![image](https://user-images.githubusercontent.com/80921933/229427174-fe2e344b-5835-4129-99cb-450834c683a8.png)

Isso acontece porque o Swagger exige configurações adicionais para funcionar via HTTPS (link para referência: https://stackoverflow.com/questions/71857622/java-spring-boot-with-swagger-failed-to-load-remote-configuration).

Eu estava um pouco com pressa, então não procurei a fundo sobre como resolver isso.

Alternativamente, você pode acessar o Swagger **sem HTTPS** diretamente pelo DNS do servidor backend: http://backend.wipro.alexthedeveloper.com.br/swagger

# Enviando uma requisição para a API

Experimente enviar um POST request com o seguinte Request Body para o endereço **https://proxy.wipro.alexthedeveloper.com.br/v1/consulta-endereco**:

```json
{
  "numeroCep": "01001000"
}
```

Resultado:

![image](https://user-images.githubusercontent.com/80921933/229579265-dca283f1-dfe5-490f-9dc5-1bff096c4fdf.png)

O endereço acima possui um certificado SSL instalado. Existe também uma versão HTTP da API, hospedada em **backend.wipro.alexthedeveloper.com.br/v1/consulta-endereco**. Basta enviar o mesmo Request-Body para lá.

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

# Problema do frete

Resolvi o problema do frete com um Map, onde a **chave** era o valor do frete e o **valor** era uma lista de estados que correspondem ao valor em questão.

```java
  private static Map<Double, List<String>> valoresFretePorEstado = new HashMap<>() {{
      put(7.85, List.of("ES", "MG", "RJ", "SP"));
      put(12.50, List.of("GO", "MT", "MS", "DF"));
      put(15.98, List.of("MA", "PI", "CE", "RN", "RN", "PB", "AL", "SE", "BA"));
      put(17.30, List.of("PR", "RS", "SC"));
      put(20.83, List.of("AC", "AP", "AM", "PA", "RO", "RR", "TO")); 
  }}; 
```

Depois, fiz um **Converter** no ModelMapper, que tem a responsabilidade de receber a sigla de um estado e convertê-la a seu frete:

```java
protected Double convert(String estado) {

    Double frete = 0d;

    for (Map.Entry<Double, List<String>> entry : valoresFretePorEstado.entrySet()) {
        if(entry.getValue().contains(estado)){
            frete = entry.getKey();
            break;
        }
    }

    return frete;
}
```

Por fim, bastou adicionar o **Converter** nas regras de conversão do ModelMapper:

```java
typeMap.addMappings(mapper -> mapper.using(FreteConverter.converter()).map(EnderecoResponseViaCep::getUf, EnderecoResponse::setFrete));
```

**Lê-se:** Usando o `FreteConverter.converter()`, extraia o valor do campo **uf** e retorne-me o frete correspondente a ela.

# Problema de exigência de campos com nomes divergentes do retorno da API do ViaCep

No enunciado da entrega, certos campos são nomeados divergentemente do retorno da API ViaCep:

- **logradouro** >>>> **rua**
- **localidade** >>>> **cidade**
- **uf** >>>> **estado**

Resolvi esse problema também pelo ModelMapper, que é capaz de mapear campos com nomes divergentes:

```java
typeMap.addMapping(EnderecoResponseViaCep::getLogradouro, EnderecoResponse::setRua);
typeMap.addMapping(EnderecoResponseViaCep::getLocalidade, EnderecoResponse::setCidade);
typeMap.addMapping(EnderecoResponseViaCep::getUf, EnderecoResponse::setEstado);
```

Não é um problema complexo, mas é um detalhe que pode passar despercebido.

# Mensagens de erro personalizadas

Um erro será retornado majoritariamente nos seguintes casos:

- CEP informado é nulo
- CEP informado é vazio
- CEP informado está fora do padrão XXXXXXXX ou XXXXX-XXX
- CEP informado não existe

Todos esses casos foram devidamente tratados com validações:

**CEP informado é nulo**

![image](https://user-images.githubusercontent.com/80921933/229386270-92dc3627-f7a5-4908-86e5-e8fde0ec7a56.png)

**CEP informado é vazio**

![image](https://user-images.githubusercontent.com/80921933/229386292-d3b1c1d5-8f7b-4bc3-bc28-cec344884fc7.png)

**CEP informado está fora do padrão XXXXXXXX ou XXXXX-XXX**

![image](https://user-images.githubusercontent.com/80921933/229386328-ee43a752-4691-40a1-b1ec-8df8255838b4.png)

**CEP informado não existe**

![image](https://user-images.githubusercontent.com/80921933/229386352-33054fc8-fbda-447d-a2e7-b5b37f102ec1.png)

Os testes também encarregaram-se de testar os cenários acima evidenciados.

Além disso, faz-se importante evidenciar a utilização do **Regex** para a validação de formato do CEP:

```java
@NotNull(message = "O campo numeroCep não pode ser nulo.")
@NotBlank(message = "O campo numeroCep não pode ser vazio.")
@Pattern(regexp = "^([0-9]{5}-[0-9]{3}|[0-9]{8})$", message = "O formato do CEP deve ser XXXXX-XXX ou XXXXXXXX.")
private String numeroCep;
```

# Configuração do NGINX como proxy-reverso

Optei por usar o NGINX como proxy-reverso da aplicação. Gerei um certificado SSL via certbot para o DNS **proxy.wipro.alexthedeveloper.com.br** e implementei-o nas configurações do NGINX:

```nginx
server {
    listen 443 ssl;
    server_name proxy.wipro.alexthedeveloper.com.br;
    ssl_certificate /etc/letsencrypt/live/proxy.wipro.alexthedeveloper.com.br/fullchain.pem;
    ssl_certificate_key /etc/letsencrypt/live/proxy.wipro.alexthedeveloper.com.br/privkey.pem;

    location /v1/consulta-endereco {
        proxy_pass http://backend.wipro.alexthedeveloper.com.br/v1/consulta-endereco;
        proxy_set_header Host backend.wipro.alexthedeveloper.com.br;
    }

    location /swagger {
        proxy_set_header        X-Forwarded-Proto $scheme;
        proxy_pass http://backend.wipro.alexthedeveloper.com.br/swagger;
        proxy_set_header Host backend.wipro.alexthedeveloper.com.br;
    }

    location /swagger-ui/index.html {
        proxy_set_header        X-Forwarded-Proto $scheme;
        proxy_pass http://backend.wipro.alexthedeveloper.com.br/swagger-ui/index.html;
        proxy_set_header Host backend.wipro.alexthedeveloper.com.br;
    }

}

```

Ao acessar o DNS **https://proxy.wipro.alexthedeveloper.com.br**, você perceberá que o browser confia no certificado emitido, já que ele é assinado por um root CA:

![image](https://user-images.githubusercontent.com/80921933/229428426-291262e3-e816-4b8e-b5c1-b799919f17ec.png)

A idéia do NGINX é a de servir como um intermediário entre o cliente e o servidor. Nesse caso, usei-o especificamente para a implementação de um certificado SSL:

![image](https://user-images.githubusercontent.com/80921933/229428579-1ae12569-0815-4aeb-9ed4-a370a3863e09.png)

Você poderá enviar um POST request normalmente para o proxy-reverso, seguindo o seguinte Request Body:

```json
{
  "numeroCep": "01001000"
}
```

# Deploy da infraestrutura do projeto via Terraform

Dentro da pasta **terraform**, encontram-se arquivos usados para o deploy da infraestrutura do projeto.

Dentre os recursos, estão: 

- 3 servidores na AWS, hosteando o Jenkins, NGINX e a API feita em Spring Boot;
- 1 Hosted-Zone: **alexthedeveloper.com.br**
- 3 DNS records: **proxy.wipro.alexthedeveloper.com.br**, **jenkins.wipro.alexthedeveloper.com.br** e **backend.wipro.alexthedeveloper.com.br**, que representam o NGINX, Jenkins e o backend, respectivamente;
- 3 security-groups para os servidores;
- SSH key-pair para acessar os servidores via SSH;
- Outras coisas menores, como outputs do Terraform, provider, etc...

Por favor, não deixe de acessar a pasta **terraform**!

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
                    docker push azold6/wipro-backend:$BUILD_NUMBER 
                """ 
            }
        }

        stage('Deploy') { 
            steps {
                sh """
                    ssh -o StrictHostKeyChecking=no ec2-user@backend.wipro.alexthedeveloper.com.br "docker stop wipro-backend"
                    ssh -o StrictHostKeyChecking=no ec2-user@backend.wipro.alexthedeveloper.com.br "docker run -p 80:80 -d --name wipro-backend --rm azold6/wipro-backend:$BUILD_NUMBER"
                """                
            }
        }
    }
}
```

Dentro do Jenkins, criei um projeto de **Pipeline** que usa Webhooks como trigger:

![image](https://user-images.githubusercontent.com/80921933/229385388-e344845c-899b-48ee-baca-f02bb6543ea4.png)

Assim que eu fizer qualquer push para o repositório, os Webhooks enviarão um request para o servidor que hospeda o Jenkins, iniciando o processo de entrega contínua.

A pipeline se encarrega de testar, buildar e deployar a aplicação, utilizando-se do seguinte Dockerfile:

```dockerfile
FROM maven:3.8.6

WORKDIR /app

COPY . /app

CMD java -jar target/API-Wipro-0.0.1-SNAPSHOT.jar
```

# Configuration management

Usei uma série de shell-scripts para o configuration-management do projeto. Alternativamente pode-se utilizar o Ansible, mas para fins de simplificação, optei pelo shell-script mesmo.

Os scripts estão em **/terraform/scripts**.

![image](https://user-images.githubusercontent.com/80921933/229431433-6f66b7ae-5b6c-4f69-bd8d-d1d1b2c87c7d.png)

Como exemplo, deixarei o script **InstallJenkins.sh** logo abaixo:

```shell
#!/bin/bash

echo '**********INSTALANDO DOCKER**********'

sudo amazon-linux-extras install -y docker

sudo service docker start

sudo chmod a+rwx /var/run/docker.sock

echo '**********SUBINDO CONTEINER DO JENKINS**********'

mkdir /tmp/jenkins_home

docker run --name jenkins -v /tmp/jenkins_home:/var/jenkins_home -v /var/run/docker.sock:/var/run/docker.sock -d -p 8080:8080 jenkins/jenkins 

sleep 5

echo '**********INSTALANDO DOCKER NO JENKINS**********'

docker exec -u 0 jenkins bash -c "curl https://get.docker.com/ > dockerinstall && chmod 777 dockerinstall && ./dockerinstall"

docker exec -u 0 jenkins bash -c "mkdir -p /home/jenkins/bin/ && chown -R 1000:1000 /home/jenkins"

echo '**********INSTALANDO MAVEN NO JENKINS**********'

docker exec -u 0 jenkins bash -c "apt update && apt install -y maven"

echo '**********INSTALANDO VIM NO JENKINS**********'

docker exec -u 0 jenkins bash -c "apt install -y vim"

cat /tmp/jenkins_home/secrets/initialAdminPassword >> /tmp/JENKINS_PASSWORD
```

Nesse âmbito, possuo um projeto para subir um cluster K8s em instâncias EC2 com shell-script e Terraform. Está fora do escopo do teste técnico, mas gostaria de compartilhá-lo com vocês: https://github.com/azl6/k8s-cluster-creation

# Extras

- Fazer um frontend em React estava em meus planos, entretanto, tive alguns problemas pessoais, e o tempo que pude dedicar ao projeto não me permitiu fazê-lo;
- Usar o ModelMapper foi somente **uma das inúmeras opções**. Talvez pareça que a sua utilização seja um pouco não-usual, mas eu gosto bastante da ferramenta. Caso fosse opção da liderança, nada me impediria de implementar essa solução de outra forma. Mas eu acho os Converters do ModelMapper extremamente poderosos e por isso optei por utilizá-los;
- Por favor, notem os meus esforços com NGINX, Terraform, Shell Script, Linux, certificados SSL/TLS, Docker, etc... Sou um grande entusiasta da cultura DevOps e, apesar deste assunto estar fora do escopo do projeto, implementei diversas soluções aqui. Me capacitei para atuar nas mais diversas frentes da área de TI, seja Backend, Frontend ou DevOps.

**Obrigado pela oportunidade!**
