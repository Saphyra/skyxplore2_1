package org.github.saphyra.skyxplore.character.repository;

import java.io.IOException;
import java.util.Arrays;

import org.github.saphyra.skyxplore.character.domain.SkyXpCharacter;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.saphyra.converter.ConverterBase;
import com.github.saphyra.encryption.impl.IntegerEncryptor;
import com.github.saphyra.encryption.impl.StringEncryptor;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
class CharacterConverter extends ConverterBase<CharacterEntity, SkyXpCharacter> {
    private final IntegerEncryptor integerEncryptor;
    private final ObjectMapper objectMapper;
    private final StringEncryptor stringEncryptor;

    @Override
    public SkyXpCharacter processEntityConversion(CharacterEntity entity) {
        if (entity == null) {
            return null;
        }

        //TODO update with using builder
        SkyXpCharacter domain = SkyXpCharacter.builder().build();

        try {
            domain.setCharacterId(entity.getCharacterId());
            domain.setCharacterName(entity.getCharacterName());
            domain.setUserId(entity.getUserId());
            domain.addMoney(integerEncryptor.decryptEntity(
                entity.getMoney(),
                entity.getCharacterId())
            );
            String[] equipments = objectMapper.readValue(
                stringEncryptor.decryptEntity(
                    entity.getEquipments(),
                    entity.getCharacterId()
                ),
                String[].class
            );
            domain.addEquipments(
                Arrays.asList(equipments)
            );
        } catch (IOException e) {
            e.printStackTrace();
        }
        return domain;
    }

    @Override
    public CharacterEntity processDomainConversion(SkyXpCharacter domain) {
        if (domain == null) {
            throw new IllegalArgumentException("domain must not be null.");
        }
        CharacterEntity entity = new CharacterEntity();
        try {
            entity.setCharacterId(domain.getCharacterId());
            entity.setCharacterName(domain.getCharacterName());
            entity.setUserId(domain.getUserId());
            entity.setMoney(integerEncryptor.encryptEntity(
                domain.getMoney(),
                domain.getCharacterId())
            );
            entity.setEquipments(
                stringEncryptor.encryptEntity(
                    objectMapper.writeValueAsString(domain.getEquipments()),
                    domain.getCharacterId()
                )
            );
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return entity;
    }
}
