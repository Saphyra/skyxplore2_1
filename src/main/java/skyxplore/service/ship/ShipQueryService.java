package skyxplore.service.ship;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import skyxplore.controller.view.View;
import skyxplore.controller.view.ship.ShipView;
import skyxplore.controller.view.ship.ShipViewConverter;
import skyxplore.dataaccess.db.EquippedShipDao;
import skyxplore.dataaccess.db.SlotDao;
import skyxplore.domain.ship.EquippedShip;
import skyxplore.domain.slot.EquippedSlot;
import skyxplore.exception.ShipNotFoundException;
import skyxplore.service.GameDataFacade;

@Service
@Slf4j
@RequiredArgsConstructor
public class ShipQueryService {
    private final EquippedShipDao equippedShipDao;
    private final GameDataFacade gameDataFacade;
    private final ShipViewConverter shipViewConverter;
    private final SlotDao slotDao;

    public EquippedShip getShipByCharacterId(String characterId) {
        EquippedShip ship = equippedShipDao.getShipByCharacterId(characterId);
        if (ship == null) {
            throw new ShipNotFoundException("No ship found with characterId " + characterId);
        }
        return ship;
    }

    public View<ShipView> getShipData(String characterId) {
        EquippedShip ship = getShipByCharacterId(characterId);

        EquippedSlot defenseSlot = slotDao.getById(ship.getDefenseSlotId());
        EquippedSlot weaponSlot = slotDao.getById(ship.getWeaponSlotId());

        return new View<>(
            shipViewConverter.convertDomain(ship, defenseSlot, weaponSlot),
            gameDataFacade.collectEquipmentData(ship, defenseSlot, weaponSlot)
        );
    }
}
