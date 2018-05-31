package skyxplore.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import skyxplore.dataaccess.character.CharacterDao;
import skyxplore.exception.InvalidAccessException;
import skyxplore.restcontroller.request.CharacterDeleteRequest;
import skyxplore.restcontroller.request.CreateCharacterRequest;
import skyxplore.service.domain.SkyXpCharacter;

import java.util.List;

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

    public void deleteCharacter(CharacterDeleteRequest request, Long userId){
        SkyXpCharacter character = characterDao.findById(request.getCharacterId());
        if(!userId.equals(character.getUser().getUserId())){
            throw new InvalidAccessException("Unauthorized character access. CharacterId: " + character.getCharacterId() + ", userId: " + userId);
        }
        characterDao.deleteById(request.getCharacterId());
    }

    public List<SkyXpCharacter> getCharactersByUserId(Long userId){
        return characterDao.findByUserId(userId);
    }

    public boolean isCharNameExists(String charName){
        return characterDao.findByCharacterName(charName) != null;
    }
}
