package com.azl6.APIWipro.model;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import com.azl6.APIWipro.models.EnderecoResponse;

@ExtendWith(MockitoExtension.class)
public class EnderecoResponseTest {

    EnderecoResponse enderecoResponse1;
    EnderecoResponse enderecoResponse2;

    @Test
    public void testHashCode(){
        enderecoResponse1 = new EnderecoResponse();
        assertNotNull(enderecoResponse1.hashCode());
    }

    @Test
    public void testEquals(){
        enderecoResponse1 = new EnderecoResponse();
        enderecoResponse2 = new EnderecoResponse();
        assertNotNull(enderecoResponse1.hashCode());
        assertTrue(enderecoResponse1.equals(enderecoResponse2));
    }
    
}
