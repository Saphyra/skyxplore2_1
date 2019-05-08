package com.github.saphyra.skyxplore.factory.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.github.saphyra.converter.ConverterBase;
import com.github.saphyra.encryption.impl.StringEncryptor;
import com.github.saphyra.skyxplore.common.ObjectMapperDelegator;
import lombok.RequiredArgsConstructor;
import com.github.saphyra.skyxplore.factory.domain.Materials;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
class MaterialsConverter extends ConverterBase<String, Materials> {
    private final ObjectMapperDelegator objectMapperDelegator;
    private final StringEncryptor stringEncryptor;

    public Materials convertEntity(String entity, String key) {
        return convertEntity(stringEncryptor.decryptEntity(entity, key));
    }

    public String convertDomain(Materials domain, String key) {
        return stringEncryptor.encryptEntity(convertDomain(domain), key);
    }

    @Override
    public Materials processEntityConversion(String entity) {
        TypeReference<HashMap<String, Integer>> typeReference = new TypeReference<HashMap<String, Integer>>() {
        };
        Map<String, Integer> elements = objectMapperDelegator.readValue(entity, typeReference);
        return new Materials(elements);
    }

    @Override
    public String processDomainConversion(Materials domain) {
        return objectMapperDelegator.writeValueAsString(domain);
    }
}
