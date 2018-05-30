package skyxplore.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import skyxplore.dataaccess.character.CharacterDao;
import skyxplore.service.domain.SkyXpCharacter;

@Service
@Slf4j
@RequiredArgsConstructor
public class CharacterService {
    private final CharacterDao characterDao;

    public boolean isCharNameExists(String charName){
        return characterDao.findByCharacterName(charName) != null;
    }
}
