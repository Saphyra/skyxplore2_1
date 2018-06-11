package skyxplore.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import skyxplore.dataaccess.db.dao.CharacterDao;
import skyxplore.dataaccess.db.dao.EquippedShipDao;
import skyxplore.dataaccess.db.dao.SlotDao;
import skyxplore.exception.CharacterNameAlreadyExistsException;
import skyxplore.exception.InvalidAccessException;
import skyxplore.restcontroller.request.CharacterDeleteRequest;
import skyxplore.restcontroller.request.CreateCharacterRequest;
import skyxplore.restcontroller.request.RenameCharacterRequest;
import skyxplore.service.domain.EquippedShip;
import skyxplore.service.domain.EquippedSlot;
import skyxplore.service.domain.SkyXpCharacter;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class CharacterService {
    private final CharacterDao characterDao;
    private final EquippedShipDao equippedShipDao;
    private final NewCharacterGenerator newCharacterGenerator;
    private final SlotDao slotDao;

    @Transactional
    public void createCharacter(CreateCharacterRequest request, String userId){
        if(isCharNameExists(request.getCharacterName())){
            throw new CharacterNameAlreadyExistsException("Character already exists with name " + request.getCharacterName());
        }
        SkyXpCharacter character = newCharacterGenerator.createCharacter(userId, request.getCharacterName());

        EquippedShip ship = newCharacterGenerator.createShip(character.getCharacterId());
        character.setShipId(ship.getShipId());

        EquippedSlot defenseSlot = newCharacterGenerator.createDefenseSlot(ship.getShipId());
        ship.setDefenseSlotId(defenseSlot.getSlotId());

        EquippedSlot weaponSlot = newCharacterGenerator.createWeaponSlot(ship.getShipId());
        ship.setWeaponSlotId(weaponSlot.getSlotId());

        characterDao.save(character);
        equippedShipDao.save(ship);
        slotDao.save(defenseSlot);
        slotDao.save(weaponSlot);
    }

    @Transactional
    public void deleteCharacter(CharacterDeleteRequest request, String userId){
        SkyXpCharacter character = characterDao.findById(request.getCharacterId());
        if(!userId.equals(character.getUserId())){
            throw new InvalidAccessException("Unauthorized character access. CharacterId: " + character.getCharacterId() + ", userId: " + userId);
        }
        characterDao.deleteById(request.getCharacterId());
    }

    public List<SkyXpCharacter> getCharactersByUserId(String userId){
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
        if(!userId.equals(character.getUserId())){
            throw new InvalidAccessException("Unauthorized character access. CharacterId: " + character.getCharacterId() + ", userId: " + userId);
        }
        character.setCharacterName(request.getNewCharacterName());
        characterDao.renameCharacter(request.getCharacterId(), request.getNewCharacterName());
    }
}
