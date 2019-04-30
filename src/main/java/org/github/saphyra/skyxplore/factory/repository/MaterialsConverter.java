package org.github.saphyra.skyxplore.factory.repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.saphyra.converter.ConverterBase;
import com.github.saphyra.encryption.impl.StringEncryptor;
import lombok.RequiredArgsConstructor;
import org.github.saphyra.skyxplore.factory.domain.Materials;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
class MaterialsConverter extends ConverterBase<String, Materials> {
    private final ObjectMapper objectMapper;
    private final StringEncryptor stringEncryptor;

    public Materials convertEntity(String entity, String key) {
        return convertEntity(stringEncryptor.decryptEntity(entity, key));
    }

    public String convertDomain(Materials domain, String key) {
        return stringEncryptor.encryptEntity(convertDomain(domain), key);
    }

    @Override
    public Materials processEntityConversion(String entity) {
        if (entity == null) {
            return null;
        }
        try {
            TypeReference<HashMap<String, Integer>> typeReference = new TypeReference<HashMap<String, Integer>>() {
            };
            Map<String, Integer> elements = objectMapper.readValue(entity, typeReference);
            return new Materials(elements);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String processDomainConversion(Materials domain) {
        if (domain == null) {
            throw new IllegalArgumentException("domain must not be null.");
        }
        try {
            return objectMapper.writeValueAsString(domain);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
