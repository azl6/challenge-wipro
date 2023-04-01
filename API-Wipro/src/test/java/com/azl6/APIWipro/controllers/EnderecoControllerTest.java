package com.azl6.APIWipro.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.azl6.APIWipro.models.EnderecoResponse;
import com.azl6.APIWipro.services.CepService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(EnderecoController.class)
public class EnderecoControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private CepService cepService;

    private EnderecoResponse enderecoResponseValido;

    @BeforeEach
    public void setup(){

        enderecoResponseValido = new EnderecoResponse(
            "01001-000",
            "Praça da Sé",
            "lado ímpar",
            "Sé",
            "São Paulo",
            "SP",
            7.85
        );
    }

    @Test
    public void testConsultaEnderecoAPartirDoCep_DeveRetornarEnderecoValido_QuandoCepEhValido() throws Exception {
    
        BDDMockito.given(cepService.consultaEnderecoAPartirDoCep("11111111")).willReturn(enderecoResponseValido);
    
        mvc.perform(post("/v1/consulta-endereco")
        .contentType(MediaType.APPLICATION_JSON)
        .content("{\"numeroCep\": \"11111111\"}"))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.cep").value("01001-000"))
        .andExpect(jsonPath("$.rua").value("Praça da Sé"))
        .andExpect(jsonPath("$.complemento").value("lado ímpar"))
        .andExpect(jsonPath("$.bairro").value("Sé"))
        .andExpect(jsonPath("$.cidade").value("São Paulo"))
        .andExpect(jsonPath("$.estado").value("SP"))
        .andExpect(jsonPath("$.frete").value(7.85))
        .andReturn();
    }

    @Test
    public void testConsultaEnderecoAPartirDoCep_DeveRetornar400_QuandoCepEhVazio() throws Exception {
    
        BDDMockito.given(cepService.consultaEnderecoAPartirDoCep("11111111")).willReturn(enderecoResponseValido);
    
        mvc.perform(post("/v1/consulta-endereco")
        .contentType(MediaType.APPLICATION_JSON)
        .content("{\"numeroCep\": \"\"}"))
        .andExpect(status().isBadRequest())
        .andReturn();
    }

    @Test
    public void testConsultaEnderecoAPartirDoCep_DeveRetornar400_QuandoCepEhNulo() throws Exception {
    
        BDDMockito.given(cepService.consultaEnderecoAPartirDoCep("11111111")).willReturn(enderecoResponseValido);
    
        mvc.perform(post("/v1/consulta-endereco")
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest())
        .andReturn();
    }
    
}