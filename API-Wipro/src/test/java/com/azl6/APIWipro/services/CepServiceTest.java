package com.azl6.APIWipro.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

import com.azl6.APIWipro.exceptions.CepInvalidoException;
import com.azl6.APIWipro.modelmapper.EnderecoMapper;
import com.azl6.APIWipro.models.EnderecoResponse;
import com.azl6.APIWipro.models.EnderecoResponseViaCep;

@ExtendWith(MockitoExtension.class)
public class CepServiceTest {

    @InjectMocks
    private CepService cepService;

    @Mock(lenient = true)
    private EnderecoMapper enderecoMapper;

    @Mock(lenient = true)
    private RestTemplate restTemplate;

    private EnderecoResponseViaCep enredecoResponseViaCepValido;

    private EnderecoResponseViaCep enredecoResponseViaCepInvalido;

    private EnderecoResponse enredecoResponseValido;

    private String BASE_URL_VIA_CEP;

    @BeforeEach
    public void setup(){

        BASE_URL_VIA_CEP = "https://viacep.com.br/ws";
        ReflectionTestUtils.setField(cepService, "BASE_URL_VIA_CEP", "https://viacep.com.br/ws");

        enredecoResponseViaCepValido = new EnderecoResponseViaCep(
            "01001-000",
            "Praça da Sé",
            "lado ímpar",
            "Sé",
            "São Paulo",
            "SP"
        );

        enredecoResponseViaCepInvalido = new EnderecoResponseViaCep(
            null,
            null,
            null,
            null,
            null,
            null
        );

        enredecoResponseValido = new EnderecoResponse(
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
    public void testConsultaEnderecoAPartirDoCep_DeveRetornarEndereçoValido_QuandoCepEhValido(){
        BDDMockito.given(restTemplate.getForObject(BASE_URL_VIA_CEP + "/01001000/json", EnderecoResponseViaCep.class)).willReturn(enredecoResponseViaCepValido);
        BDDMockito.given(enderecoMapper.corrigeAtributosDaResposta(enredecoResponseViaCepValido)).willReturn(enredecoResponseValido);

        EnderecoResponse response = cepService.consultaEnderecoAPartirDoCep("01001000");

        assertEquals("Sé", response.getBairro());
        assertEquals("01001-000", response.getCep());
        assertEquals("São Paulo", response.getCidade());
        assertEquals("lado ímpar", response.getComplemento());
        assertEquals("SP", response.getEstado());
        assertEquals("Praça da Sé", response.getRua());

        BDDMockito.then(restTemplate).should(times(1)).getForObject(BASE_URL_VIA_CEP + "/01001000/json", EnderecoResponseViaCep.class);
        BDDMockito.then(enderecoMapper).should(times(1)).corrigeAtributosDaResposta(enredecoResponseViaCepValido);
    }

    @Test
    public void testConsultaEnderecoAPartirDoCep_DeveLançarCepInvalidoException_QuandoCepEhInvalido(){
        BDDMockito.given(restTemplate.getForObject(BASE_URL_VIA_CEP + "/11111111/json", EnderecoResponseViaCep.class)).willReturn(enredecoResponseViaCepInvalido);


        CepInvalidoException thrown = assertThrows(
            CepInvalidoException.class,
            () -> cepService.consultaEnderecoAPartirDoCep("11111111"),
            "O CEP informado não existe."
        );
 
        assertTrue(thrown.getMessage().contentEquals("O CEP informado não existe."));
        

        BDDMockito.then(restTemplate).should(times(1)).getForObject(BASE_URL_VIA_CEP + "/11111111/json", EnderecoResponseViaCep.class);
        BDDMockito.then(enderecoMapper).should(times(0)).corrigeAtributosDaResposta(enredecoResponseViaCepValido);
    }
    
}
