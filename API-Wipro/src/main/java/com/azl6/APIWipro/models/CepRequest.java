package com.azl6.APIWipro.models;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter 
@Setter 
@NoArgsConstructor
public class CepRequest {

    @NotNull(message = "O campo numeroCep não pode ser nulo.")
    @NotBlank(message = "O campo numeroCep não pode ser vazio.")
    @Pattern(regexp = "^([0-9]{5}-[0-9]{3}|[0-9]{8})$", message = "O formato do CEP deve ser XXXXX-XXX ou XXXXXXXX.")
    private String numeroCep;
    
}
