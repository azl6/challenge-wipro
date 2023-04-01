package com.azl6.APIWipro.controllers;

import javax.validation.Valid;

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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("v1")
@Tag(name = "Endereço Controller", description = "Controller para operações com endereços.")
public class EnderecoController {

    @Autowired
    private CepService cepService;

    @PostMapping("consulta-endereco")
    @Operation(summary = "Retorna o valor do frete para um endereço a partir do CEP.")
    public ResponseEntity<EnderecoResponse> consultaEnderecoAPartirDoCep(@Valid @RequestBody CepRequest cep){

        EnderecoResponse resposta = cepService.consultaEnderecoAPartirDoCep(cep.getNumeroCep());

        return ResponseEntity.status(HttpStatus.OK).body(resposta);
    }
}
