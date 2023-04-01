package com.azl6.APIWipro.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter 
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EnderecoResponseViaCep {

    private String cep; 
    private String logradouro; //rua
    private String complemento; 
    private String bairro; 
    private String localidade; //cidade
    private String uf; //estado
    
}
