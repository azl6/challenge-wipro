package com.azl6.APIWipro.modelmapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.modelmapper.AbstractConverter;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.azl6.APIWipro.models.EnderecoResponse;
import com.azl6.APIWipro.models.EnderecoResponseViaCep;

@Component
public class EnderecoMapper {

    @Autowired
    ModelMapper modelMapper;

    Map<Double, List<String>> valoresFreteEEstados = new HashMap<>() {{
        put(7.85, List.of("ES", "MG", "RJ", "SP"));
        put(12.50, List.of("GO", "MT", "MS", "DF"));
        put(15.98, List.of("MA", "PI", "CE", "RN", "RN", "PB", "AL", "SE", "BA"));
        put(17.30, List.of("PR", "RS", "SC"));
        put(20.83, List.of("AC", "AP", "AM", "PA", "RO", "RR", "TO")); 

    }}; 

    public EnderecoResponse viaCepToCorrectJson(EnderecoResponseViaCep enderecoResponseViaCep){

        TypeMap<EnderecoResponseViaCep, EnderecoResponse> typeMap = modelMapper.getTypeMap(EnderecoResponseViaCep.class, EnderecoResponse.class);

        if (typeMap == null)
            typeMap = modelMapper.createTypeMap(EnderecoResponseViaCep.class, EnderecoResponse.class);

        typeMap.addMapping(EnderecoResponseViaCep::getLogradouro, EnderecoResponse::setRua);
        typeMap.addMapping(EnderecoResponseViaCep::getLocalidade, EnderecoResponse::setCidade);
        typeMap.addMapping(EnderecoResponseViaCep::getUf, EnderecoResponse::setEstado);

        typeMap.addMappings(mapper -> mapper.using(freteConverter()).map(EnderecoResponseViaCep::getUf, EnderecoResponse::setFrete));

        return modelMapper.map(enderecoResponseViaCep, EnderecoResponse.class);
    }

    private Converter<String, Double> freteConverter(){
        return new AbstractConverter<String, Double>() {

            @Override
            protected Double convert(String estado) {

                Double frete = 0d;

                for (Map.Entry<Double, List<String>> entry : valoresFreteEEstados.entrySet()) {
                    if(entry.getValue().contains(estado)){
                        frete = entry.getKey();
                        break;
                    }
                }

                return frete;
            }
        };
    }
}
