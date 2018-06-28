package skyxplore.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import skyxplore.dataaccess.db.CharacterDao;
import skyxplore.dataaccess.db.EquippedShipDao;
import skyxplore.dataaccess.db.SlotDao;
import skyxplore.exception.InvalidAccessException;
import skyxplore.exception.ShipNotFoundException;
import skyxplore.restcontroller.view.View;
import skyxplore.restcontroller.view.ship.ShipView;
import skyxplore.restcontroller.view.ship.ShipViewConverter;
import skyxplore.domain.ship.EquippedShip;
import skyxplore.domain.slot.EquippedSlot;
import skyxplore.domain.character.SkyXpCharacter;

@Service
@Slf4j
@RequiredArgsConstructor
public class EquippedShipService {
    private final CharacterDao characterDao;
    private  final EquippedShipDao equippedShipDao;
    private final GameDataService gameDataService;
    private final ShipViewConverter shipViewConverter;
    private final SlotDao slotDao;

    public View<ShipView> getShipData(String characterId, String userId){
        SkyXpCharacter character = characterDao.findById(characterId);
        if(!character.getUserId().equals(userId)){
            throw new InvalidAccessException("Character with Id " + characterId + " cannot be accessed by user " + userId);
        }

        EquippedShip ship = equippedShipDao.getShipByCharacterId(characterId);
        if(ship == null){
            throw new ShipNotFoundException("No ship found with characterId " + characterId);
        }

        EquippedSlot defenseSlot = slotDao.getById(ship.getDefenseSlotId());
        EquippedSlot weaponSlot = slotDao.getById(ship.getWeaponSlotId());

        View<ShipView> result = new View<>();
        result.setInfo(shipViewConverter.convertDomain(ship, defenseSlot, weaponSlot));
        result.setData(gameDataService.collectEquipmentData(ship, defenseSlot, weaponSlot));
        return result;
    }
}
