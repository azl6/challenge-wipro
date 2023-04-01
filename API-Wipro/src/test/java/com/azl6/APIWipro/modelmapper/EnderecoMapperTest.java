package com.azl6.APIWipro.modelmapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import com.azl6.APIWipro.models.EnderecoResponse;
import com.azl6.APIWipro.models.EnderecoResponseViaCep;

@ExtendWith(MockitoExtension.class)
public class EnderecoMapperTest {

    @Spy
    private ModelMapper modelMapper;

    @InjectMocks
    private EnderecoMapper enderecoMapper;

    EnderecoResponseViaCep enderecoResponseViaCepSudeste;

    EnderecoResponseViaCep enderecoResponseViaCepCentroOeste;

    EnderecoResponseViaCep enderecoResponseViaCepNordeste;

    EnderecoResponseViaCep enderecoResponseViaCepSul;

    EnderecoResponseViaCep enderecoResponseViaCepNorte;

    EnderecoResponse enderecoResponseSudeste;

    EnderecoResponse enderecoResponseCentroOeste;

    EnderecoResponse enderecoResponseNordeste;

    EnderecoResponse enderecoResponseSul;

    EnderecoResponse enderecoResponseNorte;

    @BeforeEach
    public void setup() {

        enderecoResponseViaCepSudeste = new EnderecoResponseViaCep();
        enderecoResponseViaCepSudeste.setCep("11111111");
        enderecoResponseViaCepSudeste.setComplemento("Casa");
        enderecoResponseViaCepSudeste.setBairro("Jardins");
        enderecoResponseViaCepSudeste.setLogradouro("Rua Teste");
        enderecoResponseViaCepSudeste.setLocalidade("São Paulo");
        enderecoResponseViaCepSudeste.setUf("SP");

        enderecoResponseViaCepCentroOeste = new EnderecoResponseViaCep();
        enderecoResponseViaCepCentroOeste.setCep("11111111");
        enderecoResponseViaCepCentroOeste.setComplemento("Casa");
        enderecoResponseViaCepCentroOeste.setBairro("Jardins");
        enderecoResponseViaCepCentroOeste.setLogradouro("Rua Teste");
        enderecoResponseViaCepCentroOeste.setLocalidade("São Paulo");
        enderecoResponseViaCepCentroOeste.setUf("GO");

        enderecoResponseViaCepNordeste = new EnderecoResponseViaCep();
        enderecoResponseViaCepNordeste.setCep("11111111");
        enderecoResponseViaCepNordeste.setComplemento("Casa");
        enderecoResponseViaCepNordeste.setBairro("Jardins");
        enderecoResponseViaCepNordeste.setLogradouro("Rua Teste");
        enderecoResponseViaCepNordeste.setLocalidade("São Paulo");
        enderecoResponseViaCepNordeste.setUf("BA");

        enderecoResponseViaCepSul = new EnderecoResponseViaCep();
        enderecoResponseViaCepSul.setCep("11111111");
        enderecoResponseViaCepSul.setComplemento("Casa");
        enderecoResponseViaCepSul.setBairro("Jardins");
        enderecoResponseViaCepSul.setLogradouro("Rua Teste");
        enderecoResponseViaCepSul.setLocalidade("São Paulo");
        enderecoResponseViaCepSul.setUf("SC");

        enderecoResponseViaCepNorte = new EnderecoResponseViaCep();
        enderecoResponseViaCepNorte.setCep("11111111");
        enderecoResponseViaCepNorte.setComplemento("Casa");
        enderecoResponseViaCepNorte.setBairro("Jardins");
        enderecoResponseViaCepNorte.setLogradouro("Rua Teste");
        enderecoResponseViaCepNorte.setLocalidade("São Paulo");
        enderecoResponseViaCepNorte.setUf("TO");

        enderecoResponseSudeste = new EnderecoResponse();
        enderecoResponseSudeste.setCep("11111111");
        enderecoResponseSudeste.setComplemento("Casa");
        enderecoResponseSudeste.setBairro("Jardins");
        enderecoResponseSudeste.setRua("Rua Teste");
        enderecoResponseSudeste.setCidade("São Paulo");
        enderecoResponseSudeste.setEstado("SP");
        enderecoResponseSudeste.setFrete(7.85);

        enderecoResponseCentroOeste = new EnderecoResponse();
        enderecoResponseCentroOeste.setCep("11111111");
        enderecoResponseCentroOeste.setComplemento("Casa");
        enderecoResponseCentroOeste.setBairro("Jardins");
        enderecoResponseCentroOeste.setRua("Rua Teste");
        enderecoResponseCentroOeste.setCidade("São Paulo");
        enderecoResponseCentroOeste.setEstado("GO");
        enderecoResponseCentroOeste.setFrete(12.50);

        enderecoResponseNordeste = new EnderecoResponse();
        enderecoResponseNordeste.setCep("11111111");
        enderecoResponseNordeste.setComplemento("Casa");
        enderecoResponseNordeste.setBairro("Jardins");
        enderecoResponseNordeste.setRua("Rua Teste");
        enderecoResponseNordeste.setCidade("São Paulo");
        enderecoResponseNordeste.setEstado("BA");
        enderecoResponseNordeste.setFrete(15.98);

        enderecoResponseSul = new EnderecoResponse();
        enderecoResponseSul.setCep("11111111");
        enderecoResponseSul.setComplemento("Casa");
        enderecoResponseSul.setBairro("Jardins");
        enderecoResponseSul.setRua("Rua Teste");
        enderecoResponseSul.setCidade("São Paulo");
        enderecoResponseSul.setEstado("SC");
        enderecoResponseSul.setFrete(17.30);

        enderecoResponseNorte = new EnderecoResponse();
        enderecoResponseNorte.setCep("11111111");
        enderecoResponseNorte.setComplemento("Casa");
        enderecoResponseNorte.setBairro("Jardins");
        enderecoResponseNorte.setRua("Rua Teste");
        enderecoResponseNorte.setCidade("São Paulo");
        enderecoResponseNorte.setEstado("TO");
        enderecoResponseNorte.setFrete(20.83);
    }

    @Test
    public void testCorrigeAtributosDaResposta_Sudeste() {

        EnderecoResponse result = enderecoMapper.corrigeNomesDosAtributosEInsereFrete(enderecoResponseViaCepSudeste);

        assertEquals(enderecoResponseSudeste, result);
    }

    @Test
    public void testCorrigeAtributosDaResposta_CentroOeste() {

        EnderecoResponse result = enderecoMapper.corrigeNomesDosAtributosEInsereFrete(enderecoResponseViaCepCentroOeste);

        assertEquals(enderecoResponseCentroOeste, result);
    }

    @Test
    public void testCorrigeAtributosDaResposta_Nordeste() {

        EnderecoResponse result = enderecoMapper.corrigeNomesDosAtributosEInsereFrete(enderecoResponseViaCepNordeste);

        assertEquals(enderecoResponseNordeste, result);
    }

    @Test
    public void testCorrigeAtributosDaResposta_Sul() {

        EnderecoResponse result = enderecoMapper.corrigeNomesDosAtributosEInsereFrete(enderecoResponseViaCepSul);

        assertEquals(enderecoResponseSul, result);
    }

    @Test
    public void testCorrigeAtributosDaResposta_Norte() {

        EnderecoResponse result = enderecoMapper.corrigeNomesDosAtributosEInsereFrete(enderecoResponseViaCepNorte);

        assertEquals(enderecoResponseNorte, result);
    }

}