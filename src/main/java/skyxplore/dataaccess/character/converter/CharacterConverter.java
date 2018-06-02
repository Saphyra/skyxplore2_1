package skyxplore.dataaccess.character.converter;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import skyxplore.dataaccess.character.entity.CharacterEntity;
import skyxplore.dataaccess.user.converter.UserConverter;
import skyxplore.service.domain.SkyXpCharacter;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CharacterConverter {
    private final UserConverter userConverter;

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
        domain.setUser(userConverter.convertEntity(entity.getUser()));
        return domain;
    }

    public CharacterEntity convertDomain(SkyXpCharacter domain){
        CharacterEntity entity = new CharacterEntity();
        entity.setCharacterId(domain.getCharacterId());
        entity.setCharacterName(domain.getCharacterName());
        entity.setUser(userConverter.convertDomain(domain.getUser()));
        return entity;
    }
}
