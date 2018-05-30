package skyxplore.dataaccess.character.entity.converter;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import skyxplore.dataaccess.character.entity.CharacterEntity;
import skyxplore.dataaccess.user.converter.UserConverter;
import skyxplore.service.domain.SkyXpCharacter;

@Component
@RequiredArgsConstructor
public class CharacterConverter {
    private final UserConverter userConverter;

    public SkyXpCharacter convertEntity(CharacterEntity entity){
        if(entity == null){
            return null;
        }
        SkyXpCharacter skyXpCharacter = new SkyXpCharacter();
        skyXpCharacter.setCharacterId(entity.getCharacterId());
        skyXpCharacter.setCharacterName(entity.getCharacterName());
        skyXpCharacter.setUser(userConverter.convertEntity(entity.getUser()));
        return skyXpCharacter;
    }
}
