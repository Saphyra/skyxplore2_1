package skyxplore.service.ship;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import skyxplore.dataaccess.db.SlotDao;
import skyxplore.dataaccess.gamedata.subservice.ExtenderService;
import skyxplore.domain.ship.EquippedShip;
import skyxplore.domain.slot.EquippedSlot;
import skyxplore.exception.BadSlotNameException;

import static skyxplore.service.EquippedShipFacade.DEFENSE_SLOT_NAME;
import static skyxplore.service.EquippedShipFacade.WEAPON_SLOT_NAME;

@Component
@Slf4j
@RequiredArgsConstructor
class EquipUtil {
    private final ExtenderService extenderService;
    private final SlotDao slotDao;

    boolean isExtender(String itemId) {
        return extenderService.get(itemId) != null;
    }

    EquippedSlot getSlotByName(EquippedShip ship, String slotName) {
        if (slotName.contains(DEFENSE_SLOT_NAME)) {
            return slotDao.getById(ship.getDefenseSlotId());
        } else if (slotName.contains(WEAPON_SLOT_NAME)) {
            return slotDao.getById(ship.getWeaponSlotId());
        } else {
            throw new BadSlotNameException(slotName);
        }
    }
}
