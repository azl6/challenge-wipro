package com.azl6.APIWipro.models;

import javax.validation.constraints.Pattern;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data   
@AllArgsConstructor
@NoArgsConstructor
public class CepRequest {

    @Pattern(regexp = "^([0-9]{5}-[0-9]{3}|[0-9]{8})$", message = "O formato do CEP deve ser XXXXX-XXX ou XXXXXXXX.")
    private String numeroCep;
    
}
