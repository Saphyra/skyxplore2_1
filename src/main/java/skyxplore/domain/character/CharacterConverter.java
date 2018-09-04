package skyxplore.domain.character;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import skyxplore.domain.ConverterBase;
import skyxplore.encryption.StringEncryptor;

import java.io.IOException;
import java.util.ArrayList;

@Component
@RequiredArgsConstructor
@SuppressWarnings("unchecked")
//TODO unit test
public class CharacterConverter extends ConverterBase<CharacterEntity, SkyXpCharacter> {
    private final ObjectMapper objectMapper;
    private final StringEncryptor stringEncryptor;

    @Override
    public SkyXpCharacter convertEntity(CharacterEntity entity) {
        if (entity == null) {
            return null;
        }

        SkyXpCharacter domain = new SkyXpCharacter();

        try {
            domain.setCharacterId(entity.getCharacterId());
            domain.setCharacterName(entity.getCharacterName());
            domain.setUserId(entity.getUserId());
            domain.addMoney(Integer.valueOf(stringEncryptor.decryptEntity(entity.getMoney(), entity.getCharacterId())));
            domain.addEquipments(objectMapper.readValue(stringEncryptor.decryptEntity(entity.getEquipments(), entity.getCharacterId()), ArrayList.class));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return domain;
    }

    @Override
    public CharacterEntity convertDomain(SkyXpCharacter domain) {
        if(domain == null){
            throw new IllegalArgumentException("domain must not be null.");
        }
        CharacterEntity entity = new CharacterEntity();
        try {
            entity.setCharacterId(domain.getCharacterId());
            entity.setCharacterName(domain.getCharacterName());
            entity.setUserId(domain.getUserId());
            entity.setMoney(stringEncryptor.encryptEntity(domain.getMoney().toString(), domain.getCharacterId()));
            entity.setEquipments(stringEncryptor.encryptEntity(objectMapper.writeValueAsString(domain.getEquipments()), domain.getCharacterId()));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return entity;
    }
}
