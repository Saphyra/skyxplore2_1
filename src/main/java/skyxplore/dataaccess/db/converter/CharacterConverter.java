package skyxplore.dataaccess.db.converter;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import skyxplore.dataaccess.db.entity.CharacterEntity;
import skyxplore.service.domain.SkyXpCharacter;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CharacterConverter {

    public List<SkyXpCharacter> convertEntity(List<CharacterEntity> entities){
        return entities.stream().map(this::convertEntity).collect(Collectors.toList());
    }

    public SkyXpCharacter convertEntity(CharacterEntity entity){
        if(entity == null){
            return null;
        }
        SkyXpCharacter domain = new SkyXpCharacter();
        domain.setCharacterId(entity.getCharacterId());
        domain.setCharacterName(entity.getCharacterName());
        domain.setShipId(entity.getShipId());
        domain.setUserId(entity.getUserId());
        return domain;
    }

    public CharacterEntity convertDomain(SkyXpCharacter domain){
        CharacterEntity entity = new CharacterEntity();
        entity.setCharacterId(domain.getCharacterId());
        entity.setCharacterName(domain.getCharacterName());
        entity.setUserId(domain.getUserId());
        entity.setShipId(domain.getShipId());
        return entity;
    }
}