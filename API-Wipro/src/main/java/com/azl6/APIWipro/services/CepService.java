package com.azl6.APIWipro.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.azl6.APIWipro.exceptions.CepInvalidoException;
import com.azl6.APIWipro.modelmapper.EnderecoMapper;
import com.azl6.APIWipro.models.EnderecoResponse;
import com.azl6.APIWipro.models.EnderecoResponseViaCep;


@Service
public class CepService {

    @Autowired
    private EnderecoMapper enderecoMapper;

    @Autowired 
    private RestTemplate restTemplate;
    
    @Value("${BASE_URL_VIA_CEP}")
    private String BASE_URL_VIA_CEP;

    public EnderecoResponse consultaEnderecoAPartirDoCep(String numeroCep){

        String urlViaCep = BASE_URL_VIA_CEP + "/" + numeroCep + "/json";

        EnderecoResponseViaCep respostaViaCep = restTemplate.getForObject(urlViaCep, EnderecoResponseViaCep.class);

        validaCepExiste(respostaViaCep);

        return enderecoMapper.corrigeNomesDosAtributosEInsereFrete(respostaViaCep);
    }

    private void validaCepExiste(EnderecoResponseViaCep enderecoResponseViaCep){
        if(enderecoResponseViaCep.getCep() == null){
            throw new CepInvalidoException("O CEP informado não existe.");
        }
    }

}
