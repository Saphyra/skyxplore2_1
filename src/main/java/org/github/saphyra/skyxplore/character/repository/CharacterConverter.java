package org.github.saphyra.skyxplore.character.repository;

import java.util.List;

import org.github.saphyra.skyxplore.character.domain.SkyXpCharacter;
import org.github.saphyra.skyxplore.common.ObjectMapperDelegator;
import org.springframework.stereotype.Component;

import com.github.saphyra.converter.ConverterBase;
import com.github.saphyra.encryption.impl.IntegerEncryptor;
import com.github.saphyra.encryption.impl.StringEncryptor;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
class CharacterConverter extends ConverterBase<CharacterEntity, SkyXpCharacter> {
    private final IntegerEncryptor integerEncryptor;
    private final ObjectMapperDelegator objectMapperDelegator;
    private final StringEncryptor stringEncryptor;

    @Override
    public SkyXpCharacter processEntityConversion(CharacterEntity entity) {
        if (entity == null) {
            return null;
        }

        String decryptedEquipments = stringEncryptor.decryptEntity(entity.getEquipments(), entity.getCharacterId());
        List<String> equipments = objectMapperDelegator.readValue(decryptedEquipments, String[].class);

        return SkyXpCharacter.builder()
            .characterId(entity.getCharacterId())
            .userId(entity.getUserId())
            .characterName(entity.getCharacterName())
            .money(integerEncryptor.decryptEntity(entity.getMoney(), entity.getCharacterId()))
            .equipments(equipments)
            .build();
    }

    @Override
    public CharacterEntity processDomainConversion(SkyXpCharacter domain) {
        if (domain == null) {
            throw new IllegalArgumentException("domain must not be null.");
        }
        String equipments = stringEncryptor.encryptEntity(
            objectMapperDelegator.writeValueAsString(domain.getEquipments()),
            domain.getCharacterId()
        );
        return CharacterEntity.builder()
            .characterId(domain.getCharacterId())
            .characterName(domain.getCharacterName())
            .userId(domain.getUserId())
            .money(integerEncryptor.encryptEntity(domain.getMoney(), domain.getCharacterId()))
            .equipments(equipments)
            .build();
    }
}
