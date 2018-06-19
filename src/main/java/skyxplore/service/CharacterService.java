package skyxplore.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import skyxplore.dataaccess.db.CharacterDao;
import skyxplore.dataaccess.db.EquippedShipDao;
import skyxplore.dataaccess.db.FactoryDao;
import skyxplore.dataaccess.db.SlotDao;
import skyxplore.domain.character.SkyXpCharacter;
import skyxplore.domain.factory.Factory;
import skyxplore.domain.ship.EquippedShip;
import skyxplore.domain.slot.EquippedSlot;
import skyxplore.exception.CharacterNameAlreadyExistsException;
import skyxplore.exception.InvalidAccessException;
import skyxplore.exception.NotEnoughMoneyException;
import skyxplore.restcontroller.request.CharacterDeleteRequest;
import skyxplore.restcontroller.request.CreateCharacterRequest;
import skyxplore.restcontroller.request.RenameCharacterRequest;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
@Slf4j
@RequiredArgsConstructor
public class CharacterService {
    private final CharacterDao characterDao;
    private final EquippedShipDao equippedShipDao;
    private final FactoryDao factoryDao;
    private final GameDataService gameDataService;
    private final NewCharacterGenerator newCharacterGenerator;
    private final SlotDao slotDao;

    public void buyItems(Map<String, Integer> items, String characterId, String userId) {
        SkyXpCharacter character = findCharacterByIdAuthorized(characterId, userId);
        Integer cost = validateMoney(items, character);

        character.spendMoney(cost);
        character.buyEquipments(items);
        log.info("Saving character {}", character);
        characterDao.save(character);
    }

    private Integer validateMoney(Map<String, Integer> items, SkyXpCharacter character) {
        Integer cost = countCost(items);
        if (cost > character.getMoney()) {
            throw new NotEnoughMoneyException(character.getCharacterId() + " wanted to buy items cost " + cost + ", while he had only " + character.getMoney());
        }
        return cost;
    }

    private Integer countCost(Map<String, Integer> items) {
        Set<String> keys = items.keySet();
        return keys.stream()
                .map(k -> gameDataService.findBuyable(k).getBuyPrice() * items.get(k))
                .reduce(0, (a, b) -> a + b);
    }

    @Transactional
    public void createCharacter(CreateCharacterRequest request, String userId) {
        if (isCharNameExists(request.getCharacterName())) {
            throw new CharacterNameAlreadyExistsException("Character already exists with name " + request.getCharacterName());
        }
        SkyXpCharacter character = newCharacterGenerator.createCharacter(userId, request.getCharacterName());

        EquippedShip ship = newCharacterGenerator.createShip(character.getCharacterId());
        character.setShipId(ship.getShipId());

        EquippedSlot defenseSlot = newCharacterGenerator.createDefenseSlot(ship.getShipId());
        ship.setDefenseSlotId(defenseSlot.getSlotId());

        EquippedSlot weaponSlot = newCharacterGenerator.createWeaponSlot(ship.getShipId());
        ship.setWeaponSlotId(weaponSlot.getSlotId());

        Factory factory = newCharacterGenerator.createFactory(character.getCharacterId());

        characterDao.save(character);
        equippedShipDao.save(ship);
        slotDao.save(defenseSlot);
        slotDao.save(weaponSlot);
        factoryDao.save(factory);
    }

    @Transactional
    public void deleteCharacter(CharacterDeleteRequest request, String userId) {
        SkyXpCharacter character = findCharacterByIdAuthorized(request.getCharacterId(), userId);
        characterDao.deleteById(character.getCharacterId());
    }

    public List<SkyXpCharacter> getCharactersByUserId(String userId) {
        return characterDao.findByUserId(userId);
    }

    public Integer getMoneyOfCharacter(String userId, String characterId) {
        SkyXpCharacter character = findCharacterByIdAuthorized(characterId, userId);
        return character.getMoney();
    }

    public boolean isCharNameExists(String charName) {
        return characterDao.findByCharacterName(charName) != null;
    }

    public void renameCharacter(RenameCharacterRequest request, String userId) {
        if (isCharNameExists(request.getNewCharacterName())) {
            throw new CharacterNameAlreadyExistsException("Character name already exists: " + request.getNewCharacterName());
        }
        SkyXpCharacter character = findCharacterByIdAuthorized(request.getCharacterId(), userId);
        character.setCharacterName(request.getNewCharacterName());
        characterDao.renameCharacter(request.getCharacterId(), request.getNewCharacterName());
    }

    private SkyXpCharacter findCharacterByIdAuthorized(String characterId, String userId) {
        SkyXpCharacter character = characterDao.findById(characterId);
        if (!userId.equals(character.getUserId())) {
            throw new InvalidAccessException("Unauthorized character access. CharacterId: " + character.getCharacterId() + ", userId: " + userId);
        }
        return character;
    }
}
