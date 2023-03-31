package com.azl6.APIWipro.modelmapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.modelmapper.AbstractConverter;
import org.modelmapper.Converter;

public class FreteConverter {

    private static Map<Double, List<String>> valoresFretePorEstado = new HashMap<>() {{
        put(7.85, List.of("ES", "MG", "RJ", "SP"));
        put(12.50, List.of("GO", "MT", "MS", "DF"));
        put(15.98, List.of("MA", "PI", "CE", "RN", "RN", "PB", "AL", "SE", "BA"));
        put(17.30, List.of("PR", "RS", "SC"));
        put(20.83, List.of("AC", "AP", "AM", "PA", "RO", "RR", "TO")); 

    }}; 

    public static Converter<String, Double> converter(){
        return new AbstractConverter<String, Double>() {

            @Override
            protected Double convert(String estado) {

                Double frete = 0d;

                for (Map.Entry<Double, List<String>> entry : valoresFretePorEstado.entrySet()) {
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
