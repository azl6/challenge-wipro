package com.azl6.APIWipro.modelmapper;

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

    public EnderecoResponse corrigeAtributosDaResposta(EnderecoResponseViaCep enderecoResponseViaCep){

        TypeMap<EnderecoResponseViaCep, EnderecoResponse> typeMap = modelMapper.getTypeMap(EnderecoResponseViaCep.class, EnderecoResponse.class);

        if (typeMap == null)
            typeMap = modelMapper.createTypeMap(EnderecoResponseViaCep.class, EnderecoResponse.class);

        typeMap.addMapping(EnderecoResponseViaCep::getLogradouro, EnderecoResponse::setRua);
        typeMap.addMapping(EnderecoResponseViaCep::getLocalidade, EnderecoResponse::setCidade);
        typeMap.addMapping(EnderecoResponseViaCep::getUf, EnderecoResponse::setEstado);

        typeMap.addMappings(mapper -> mapper.using(FreteConverter.converter()).map(EnderecoResponseViaCep::getUf, EnderecoResponse::setFrete));

        return modelMapper.map(enderecoResponseViaCep, EnderecoResponse.class);
    }

}
