package skyxplore.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import skyxplore.dataaccess.character.CharacterDao;
import skyxplore.restcontroller.request.CreateCharacterRequest;
import skyxplore.service.domain.SkyXpCharacter;

@Service
@Slf4j
@RequiredArgsConstructor
public class CharacterService {
    private final CharacterDao characterDao;
    private final UserService userService;

    public void createCharacter(CreateCharacterRequest request, Long userId){
        SkyXpCharacter character = new SkyXpCharacter();
        character.setCharacterName(request.getCharacterName());
        character.setUser(userService.getUserById(userId));
        characterDao.save(character);
    }

    public boolean isCharNameExists(String charName){
        return characterDao.findByCharacterName(charName) != null;
    }
}
