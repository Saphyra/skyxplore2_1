package org.github.saphyra.skyxplore.common;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class ObjectMapperDelegator {
    private final ObjectMapper objectMapper;

    public <T> List<T> readValue(String source, Class<T[]> clazz) {
        try {
            return Arrays.asList(objectMapper.readValue(source, clazz));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String writeValueAsString(Object value) {
        try {
            return objectMapper.writeValueAsString(value);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
