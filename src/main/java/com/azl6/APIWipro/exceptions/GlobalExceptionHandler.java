package com.azl6.APIWipro.exceptions;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    
    @ExceptionHandler(CepInvalidoException.class)
    public ResponseEntity<ErroPadrao> cepInvalidoException(CepInvalidoException e){
        
        ErroPadrao erro = new ErroPadrao(e.getMessage(), HttpStatus.BAD_REQUEST.value(), formatCurrentTimeMillis(System.currentTimeMillis()));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);

    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<ErroPadrao>> methodArgumentNotValidException(MethodArgumentNotValidException e){

        List<ObjectError> todosOsErrosDaException = e.getBindingResult().getAllErrors();

        List<ErroPadrao> errosResponse = new ArrayList<>();

        todosOsErrosDaException.forEach(erro -> {
            errosResponse.add(new ErroPadrao(erro.getDefaultMessage(), HttpStatus.BAD_REQUEST.value(), formatCurrentTimeMillis(System.currentTimeMillis())));
        });

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errosResponse);

    }

    private String formatCurrentTimeMillis(Long currentTimeMillis){
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");    
        Date resultdate = new Date(currentTimeMillis);
        return sdf.format(resultdate);
    }
}
