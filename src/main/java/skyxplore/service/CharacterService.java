package skyxplore.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import skyxplore.dataaccess.character.CharacterDao;
import skyxplore.dataaccess.equippedship.EquippedShipDao;
import skyxplore.exception.CharacterNameAlreadyExistsException;
import skyxplore.exception.InvalidAccessException;
import skyxplore.restcontroller.request.CharacterDeleteRequest;
import skyxplore.restcontroller.request.CreateCharacterRequest;
import skyxplore.restcontroller.request.RenameCharacterRequest;
import skyxplore.service.domain.EquippedShip;
import skyxplore.service.domain.SkyXpCharacter;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class CharacterService {
    private final CharacterDao characterDao;
    private final UserService userService;
    private final NewCharacterShipGenerator newCharacterShipGenerator;
    private final EquippedShipDao equippedShipDao;

    public void createCharacter(CreateCharacterRequest request, Long userId){
        SkyXpCharacter character = new SkyXpCharacter();
        character.setCharacterName(request.getCharacterName());
        character.setUser(userService.getUserById(userId));
        SkyXpCharacter saved = characterDao.save(character);
        EquippedShip ship = newCharacterShipGenerator.generateShip(saved.getCharacterId());
        equippedShipDao.save(ship);
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

    public void renameCharacter(RenameCharacterRequest request, Long userId){
        if(isCharNameExists(request.getNewCharacterName())){
            throw new CharacterNameAlreadyExistsException("Character name already exists: " + request.getNewCharacterName());
        }
        SkyXpCharacter character = characterDao.findById(request.getCharacterId());
        if(!userId.equals(character.getUser().getUserId())){
            throw new InvalidAccessException("Unauthorized character access. CharacterId: " + character.getCharacterId() + ", userId: " + userId);
        }
        character.setCharacterName(request.getNewCharacterName());
        characterDao.renameCharacter(request.getCharacterId(), request.getNewCharacterName());
    }
}
