package com.azl6.APIWipro.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import com.azl6.APIWipro.exceptions.ErroPadrao;

@ExtendWith(MockitoExtension.class)
public class ErroPadraoTest {

    private ErroPadrao erro1;
    private ErroPadrao erro2;

    @Test
    public void testAllMethods(){
        erro1 = new ErroPadrao("CEP Inválido", HttpStatus.BAD_REQUEST.value(), "31/03/2023 16:16");
        erro2 = new ErroPadrao();

        erro2.setMensagem("CEP Inválido");
        erro2.setStatus(HttpStatus.BAD_REQUEST.value());
        erro2.setTimestamp("31/03/2023 16:16");

        assertTrue(erro1.equals(erro2));
        assertNotNull(erro1.toString());
        assertNotNull(erro1.hashCode());
        assertEquals(erro1.getMensagem(), erro2.getMensagem());
        assertEquals(erro1.getStatus(), erro2.getStatus());
        assertEquals(erro1.getTimestamp(), erro2.getTimestamp());
        assertNotNull(erro1.getMensagem());
        assertNotNull(erro1.getStatus());
        assertNotNull(erro1.getTimestamp());
    }
    
}
