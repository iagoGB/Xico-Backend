package br.com.smd.xico.utils;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import br.com.smd.xico.dto.UserTO;
import lombok.SneakyThrows;

@Component
public  class StringToUserTOConverter implements Converter<String, UserTO> {

    @Autowired
    private ObjectMapper objectMapper;
    @Override
    @SneakyThrows
    public UserTO convert(String source) {
        return objectMapper.readValue(source, UserTO.class);
    }
}