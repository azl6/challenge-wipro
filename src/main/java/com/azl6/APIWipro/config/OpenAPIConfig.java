package com.azl6.APIWipro.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;

@Configuration
public class OpenAPIConfig {

  @Bean
  public OpenAPI myOpenAPI() {
    Server localServer = new Server();
    localServer.setUrl("http://localhost:8181");
    localServer.setDescription("Servidor local");

    Contact contact = new Contact();
    contact.setEmail("alex.rodrigues23997@gmail.com");
    contact.setName("Alex Rodrigues");
    contact.setUrl("https://github.com/azl6");


    Info info = new Info()
        .title("Desafio Wipro - Alex Rodrigues")
        .version("1.0")
        .contact(contact)
        .description("API para o cálculo de fretes por CEP.");

    return new OpenAPI().info(info).servers(List.of(localServer));
  }
}