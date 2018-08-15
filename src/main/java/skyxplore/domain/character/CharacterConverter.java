package skyxplore.domain.character;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import skyxplore.domain.ConverterBase;

import java.io.IOException;
import java.util.ArrayList;

@Component
@RequiredArgsConstructor
@SuppressWarnings("unchecked")
//TODO unit test
public class CharacterConverter extends ConverterBase<CharacterEntity, SkyXpCharacter> {
    private final ObjectMapper objectMapper;

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
            domain.addMoney(entity.getMoney());
            domain.addEquipments(objectMapper.readValue(entity.getEquipments(), ArrayList.class));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return domain;
    }

    @Override
    public CharacterEntity convertDomain(SkyXpCharacter domain) {
        CharacterEntity entity = new CharacterEntity();
        try {
            entity.setCharacterId(domain.getCharacterId());
            entity.setCharacterName(domain.getCharacterName());
            entity.setUserId(domain.getUserId());
            entity.setMoney(domain.getMoney());
            entity.setEquipments(objectMapper.writeValueAsString(domain.getEquipments()));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return entity;
    }
}
