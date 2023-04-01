package com.azl6.APIWipro.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class ErroPadrao {

    private String mensagem;
    private Integer status;
    private String timestamp;
    
}
