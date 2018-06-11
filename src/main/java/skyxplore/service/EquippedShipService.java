package skyxplore.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import skyxplore.dataaccess.db.dao.CharacterDao;
import skyxplore.dataaccess.db.dao.EquippedShipDao;
import skyxplore.dataaccess.db.dao.SlotDao;
import skyxplore.dataaccess.gamedata.GameDataService;
import skyxplore.exception.InvalidAccessException;
import skyxplore.exception.ShipNotFoundException;
import skyxplore.restcontroller.view.EquipmentView;
import skyxplore.restcontroller.view.ShipView;
import skyxplore.restcontroller.view.converter.ShipViewConverter;
import skyxplore.service.domain.EquippedShip;
import skyxplore.service.domain.EquippedSlot;
import skyxplore.service.domain.SkyXpCharacter;

@Service
@Slf4j
@RequiredArgsConstructor
public class EquippedShipService {
    private final CharacterDao characterDao;
    private  final EquippedShipDao equippedShipDao;
    private final GameDataService gameDataService;
    private final ShipViewConverter shipViewConverter;
    private final SlotDao slotDao;

    public EquipmentView<ShipView> getShipData(String characterId, String userId){
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

        EquipmentView<ShipView> result = new EquipmentView<>();
        result.setInfo(shipViewConverter.convertDomain(ship, defenseSlot, weaponSlot));
        result.setData(gameDataService.collectEquipmentData(ship, defenseSlot, weaponSlot));
        return result;
    }
}
