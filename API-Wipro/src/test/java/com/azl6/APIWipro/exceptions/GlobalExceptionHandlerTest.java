package com.azl6.APIWipro.exceptions;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;


import static org.junit.jupiter.api.Assertions.assertEquals;
@ExtendWith(MockitoExtension.class)
public class GlobalExceptionHandlerTest {

    @Mock
    private MethodArgumentNotValidException methodArgumentNotValidException;

    @Mock
    private BindingResult bindingResult;

    @InjectMocks
    private GlobalExceptionHandler globalExceptionHandler;

    CepInvalidoException cepInvalidoException;

    @Test
    public void testCepInvalidoException() {

        cepInvalidoException = new CepInvalidoException("O CEP informado não existe.");
        ResponseEntity<ErroPadrao> responseEntity = globalExceptionHandler.cepInvalidoException(cepInvalidoException);

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals("O CEP informado não existe.", responseEntity.getBody().getMensagem());
        assertEquals(400, responseEntity.getBody().getStatus());
    }

}





