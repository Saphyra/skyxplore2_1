package skyxplore.service.character;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import skyxplore.cache.CharacterNameCache;
import skyxplore.controller.request.character.CreateCharacterRequest;
import skyxplore.dataaccess.db.CharacterDao;
import skyxplore.dataaccess.db.EquippedShipDao;
import skyxplore.dataaccess.db.FactoryDao;
import skyxplore.dataaccess.db.SlotDao;
import skyxplore.domain.character.SkyXpCharacter;
import skyxplore.domain.factory.Factory;
import skyxplore.domain.ship.EquippedShip;
import skyxplore.domain.slot.EquippedSlot;
import skyxplore.exception.CharacterNameAlreadyExistsException;

@Service
@Slf4j
@RequiredArgsConstructor
public class CharacterCreatorService {
    private final CharacterDao characterDao;
    private final CharacterQueryService characterQueryService;
    private final EquippedShipDao equippedShipDao;
    private final FactoryDao factoryDao;
    private final NewCharacterGenerator newCharacterGenerator;
    private final SlotDao slotDao;
    private final CharacterNameCache characterNameCache;

    @Transactional
    public SkyXpCharacter createCharacter(CreateCharacterRequest request, String userId) {
        if (characterQueryService.isCharNameExists(request.getCharacterName())) {
            throw new CharacterNameAlreadyExistsException("Character already exists with name " + request.getCharacterName());
        }
        SkyXpCharacter character = newCharacterGenerator.createCharacter(userId, request.getCharacterName());

        EquippedShip ship = newCharacterGenerator.createShip(character.getCharacterId());

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

        characterNameCache.invalidate(request.getCharacterName());

        return character;
    }
}
