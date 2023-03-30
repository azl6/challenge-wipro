package com.azl6.APIWipro.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.azl6.APIWipro.modelmapper.EnderecoMapper;
import com.azl6.APIWipro.models.EnderecoResponse;
import com.azl6.APIWipro.models.EnderecoResponseViaCep;


@Service
public class CepService {

    @Autowired
    private EnderecoMapper enderecoMapper;
    
    @Value("${BASE_URL_VIA_CEP}")
    private String BASE_URL_VIA_CEP;

    public EnderecoResponse consultaEnderecoAPartirDoCep(String numeroCep){

        RestTemplate restTemplate = new RestTemplate();

        String urlViaCep = BASE_URL_VIA_CEP + "/" + numeroCep + "/json";

        EnderecoResponseViaCep respostaViaCep = restTemplate.getForObject(urlViaCep, EnderecoResponseViaCep.class);

        return enderecoMapper.viaCepToCorrectJson(respostaViaCep);
    }

}
