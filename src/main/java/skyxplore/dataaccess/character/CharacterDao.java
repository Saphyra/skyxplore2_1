package skyxplore.dataaccess.character;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import skyxplore.dataaccess.character.entity.converter.CharacterConverter;
import skyxplore.dataaccess.character.repository.CharacterRepository;
import skyxplore.service.domain.SkyXpCharacter;

@Component
@Slf4j
@RequiredArgsConstructor
public class CharacterDao {
    private final CharacterRepository characterRepository;
    private final CharacterConverter characterConverter;

    public SkyXpCharacter findByCharacterName(String characterName){
        return characterConverter.convertEntity(characterRepository.findByCharacterName(characterName));
    }
}
