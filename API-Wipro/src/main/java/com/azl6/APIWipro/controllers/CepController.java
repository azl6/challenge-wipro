package com.azl6.APIWipro.controllers;

import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.azl6.APIWipro.models.CepRequest;
import com.azl6.APIWipro.models.EnderecoResponse;
import com.azl6.APIWipro.services.CepService;

@RestController
@RequestMapping("v1")
public class CepController {

    @Autowired
    private CepService cepService;

    @PostMapping("consulta-endereco")
    public ResponseEntity<EnderecoResponse> hello(@RequestBody CepRequest cep){

        EnderecoResponse resposta = cepService.consultaEnderecoAPartirDoCep(cep.getNumeroCep());

        return ResponseEntity.status(HttpStatus.OK).body(resposta);
    }
}
