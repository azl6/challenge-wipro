package com.azl6.APIWipro.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Data
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class EnderecoResponse {

    private String cep; 
    private String rua; // logradouro
    private String complemento; 
    private String bairro; 
    private String cidade; //localidade
    private String estado; //uf
    private Double frete;

}
