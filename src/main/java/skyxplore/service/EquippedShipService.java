package skyxplore.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import skyxplore.dataaccess.db.CharacterDao;
import skyxplore.dataaccess.db.EquippedShipDao;
import skyxplore.dataaccess.db.SlotDao;
import skyxplore.domain.character.SkyXpCharacter;
import skyxplore.domain.ship.EquippedShip;
import skyxplore.domain.slot.EquippedSlot;
import skyxplore.exception.BadSlotException;
import skyxplore.exception.InvalidAccessException;
import skyxplore.exception.ShipNotFoundException;
import skyxplore.restcontroller.request.UnequipRequest;
import skyxplore.restcontroller.view.View;
import skyxplore.restcontroller.view.ship.ShipView;
import skyxplore.restcontroller.view.ship.ShipViewConverter;

import javax.transaction.Transactional;

@SuppressWarnings("WeakerAccess")
@Service
@Slf4j
@RequiredArgsConstructor
public class EquippedShipService {
    private static final String DEFENSE_SLOT_NAME = "defense";
    private static final String WEAPON_SLOT_NAME = "weapon";
    private static final String CONNECTOR_SLOT_NAME = "connector";

    private static final String FRONT_SLOT_NAME = "front";
    private static final String BACK_SLOT_NAME = "back";
    private static final String LEFT_SLOT_NAME = "left";
    private static final String RIGHT_SLOT_NAME = "right";

    private final CharacterDao characterDao;
    private final EquippedShipDao equippedShipDao;
    private final GameDataService gameDataService;
    private final ShipViewConverter shipViewConverter;
    private final SlotDao slotDao;

    public View<ShipView> getShipData(String characterId, String userId) {
        getCharacterByIdAuthorized(userId, characterId);

        EquippedShip ship = equippedShipDao.getShipByCharacterId(characterId);
        if (ship == null) {
            throw new ShipNotFoundException("No ship found with characterId " + characterId);
        }

        EquippedSlot defenseSlot = slotDao.getById(ship.getDefenseSlotId());
        EquippedSlot weaponSlot = slotDao.getById(ship.getWeaponSlotId());

        View<ShipView> result = new View<>();
        result.setInfo(shipViewConverter.convertDomain(ship, defenseSlot, weaponSlot));
        result.setData(gameDataService.collectEquipmentData(ship, defenseSlot, weaponSlot));
        return result;
    }

    @Transactional
    public void unequip(UnequipRequest request, String userId, String characterId) {
        SkyXpCharacter character = getCharacterByIdAuthorized(userId, characterId);
        EquippedShip ship = equippedShipDao.getShipByCharacterId(characterId);
        if (ship == null) {
            throw new ShipNotFoundException("No ship found with characterId " + characterId);
        }

        if (request.getSlot().contains(CONNECTOR_SLOT_NAME)) {
            ship.removeConnector(request.getItemId());
            equippedShipDao.save(ship);
        }else{
            EquippedSlot slot = getSlotToUnequip(ship, request);
            removeElementFromSlot(slot, request);
            slotDao.save(slot);
        }
        character.addEquipment(request.getItemId());
        characterDao.save(character);
    }

    private EquippedSlot getSlotToUnequip(EquippedShip ship, UnequipRequest request) {
        if (request.getSlot().contains(DEFENSE_SLOT_NAME)) {
            return slotDao.getById(ship.getDefenseSlotId());
        } else if (request.getSlot().contains(WEAPON_SLOT_NAME)) {
            return slotDao.getById(ship.getWeaponSlotId());
        } else {
            throw new BadSlotException(request.getSlot());
        }
    }

    private void removeElementFromSlot(EquippedSlot slot, UnequipRequest request) {
        if (request.getSlot().contains(FRONT_SLOT_NAME)) {
            slot.removeFront(request.getItemId());
        } else if (request.getSlot().contains(BACK_SLOT_NAME)) {
            slot.removeBack(request.getItemId());
        } else if (request.getSlot().contains(LEFT_SLOT_NAME)) {
            slot.removeLeft(request.getItemId());
        } else if (request.getSlot().contains(RIGHT_SLOT_NAME)) {
            slot.removeRight(request.getItemId());
        } else {
            throw new BadSlotException(request.getSlot());
        }
    }

    public SkyXpCharacter getCharacterByIdAuthorized(String userId, String characterId) {
        SkyXpCharacter character = characterDao.findById(characterId);
        if (!character.getUserId().equals(userId)) {
            throw new InvalidAccessException("Character with Id " + characterId + " cannot be accessed by user " + userId);
        }
        return character;
    }
}
