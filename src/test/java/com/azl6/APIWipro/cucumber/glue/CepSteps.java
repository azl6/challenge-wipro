package com.azl6.APIWipro.cucumber.glue;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.assertj.core.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;

import com.azl6.APIWipro.exceptions.ErroPadrao;
import com.azl6.APIWipro.models.CepRequest;
import com.azl6.APIWipro.models.EnderecoResponse;

import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class CepSteps {

    @Autowired
    private TestRestTemplate testRestTemplate;

    private EnderecoResponse expectedValidResponse;

    private EnderecoResponse actualResponse;

    private String cepRequest;

    String erroCepVazio;

    String erroFormatoCepIncorreto;

    String erroCepNulo;

    ResponseEntity<List<ErroPadrao>> responseErros;

    @Before
    public void setup(){

        erroCepVazio = "O campo numeroCep não pode ser vazio.";

        erroFormatoCepIncorreto = "O formato do CEP deve ser XXXXX-XXX ou XXXXXXXX.";

        erroCepNulo = "O campo numeroCep não pode ser nulo.";

        expectedValidResponse = new EnderecoResponse("01001-000",
        "Praça da Sé",
        "lado ímpar",
        "Sé",
        "São Paulo",
        "SP",
        7.85
);

    }

    @Given("^a valid CEP$")
    public void aValidCep(CepRequest cep){

        cepRequest = cep.getNumeroCep();

    }

    @Given("^a blank CEP$")
    public void aBlankCep(){

        cepRequest = "\"\"";

    }

    @Given("^a null CEP$")
    public void aNullCep(){

    }

    @Given("^a CEP out of the valid formats$")
    public void aCepOutOfTheValidFormats(CepRequest cep){

        cepRequest = cep.getNumeroCep();

    }

    @When("^the user sends the valid request$")
    public void theUserSendsTheRequest(){

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, Object> map = new HashMap<>();
        map.put("numeroCep", cepRequest);

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(map, headers);

        actualResponse = testRestTemplate.postForEntity("/v1/consulta-endereco", entity, EnderecoResponse.class).getBody();

    }

    @When("^the user sends the request with the blank CEP$")
    public void theUserSendsRequestWithBlankCep(){

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, Object> map = new HashMap<>();
        map.put("numeroCep", "");

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(map, headers);

        responseErros = testRestTemplate.exchange("/v1/consulta-endereco", HttpMethod.POST, entity,
        new ParameterizedTypeReference<List<ErroPadrao>>() {});

    }

    @When("^the user sends the request with the null CEP$")
    public void theUserSendsRequestWithNullCep(){

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(new HashMap<>(), headers);

        responseErros = testRestTemplate.exchange("/v1/consulta-endereco", HttpMethod.POST, entity,
        new ParameterizedTypeReference<List<ErroPadrao>>() {});

    }

    @When("^the user sends a request with the CEP of invalid format$")
    public void theUserSendsRequestWithInvalidFormatCep(){

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, Object> map = new HashMap<>();
        map.put("numeroCep", cepRequest);

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(map, headers);

        responseErros = testRestTemplate.exchange("/v1/consulta-endereco", HttpMethod.POST, entity,
        new ParameterizedTypeReference<List<ErroPadrao>>() {});

    }

    @Then("^a valid address is returned$")
    public void aValidAddressIsReturned(){
        System.out.println(expectedValidResponse.toString());
        System.out.println(actualResponse.toString());
        assertEquals(expectedValidResponse, actualResponse);
    }

    @Then("^he gets MethodArgumentNotValidExcepion with Regex and Blank errors$")
    public void heGetsMethodArgumentNotValidExceptionWithRegexAndBlankErrors(){

        List<String> erros = List.of(erroCepVazio, erroFormatoCepIncorreto);

        for(ErroPadrao erro: responseErros.getBody()){
            assertTrue(erros.contains(erro.getMensagem()));
        }

        assertEquals(HttpStatus.BAD_REQUEST.value(), responseErros.getStatusCode().value());
        assertEquals(2, responseErros.getBody().size());
    }

    @Then("^he gets MethodArgumentNotValidExcepion with Null and Blank errors$")
    public void heGetsMethodArgumentNotValidExceptionWithNullAndBlankErrors(){

        List<String> erros = List.of(erroCepVazio, erroCepNulo);

        for(ErroPadrao erro: responseErros.getBody()){
            assertTrue(erros.contains(erro.getMensagem()));
        }

        assertEquals(HttpStatus.BAD_REQUEST.value(), responseErros.getStatusCode().value());
        assertEquals(2, responseErros.getBody().size());
    }

    @Then("^he gets MethodArgumentNotValidExcepion with Regex errors$")
    public void heGetsMethodArgumentNotValidExceptionWithRegexError(){

        assertEquals(erroFormatoCepIncorreto, responseErros.getBody().get(0).getMensagem());

        assertEquals(HttpStatus.BAD_REQUEST.value(), responseErros.getStatusCode().value());
        assertEquals(1, responseErros.getBody().size());
    }
    
}
