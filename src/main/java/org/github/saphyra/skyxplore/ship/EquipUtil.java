package org.github.saphyra.skyxplore.ship;

import static org.github.saphyra.skyxplore.ship.EquippedShipFacade.DEFENSE_SLOT_NAME;
import static org.github.saphyra.skyxplore.ship.EquippedShipFacade.WEAPON_SLOT_NAME;

import org.github.saphyra.skyxplore.common.exception.BadSlotNameException;
import org.github.saphyra.skyxplore.gamedata.subservice.ExtenderService;
import org.github.saphyra.skyxplore.ship.domain.EquippedShip;
import org.github.saphyra.skyxplore.slot.domain.EquippedSlot;
import org.github.saphyra.skyxplore.slot.repository.SlotDao;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@RequiredArgsConstructor
//TODO make package-private
public class EquipUtil {
    private final ExtenderService extenderService;
    private final SlotDao slotDao;

    public boolean isExtender(String itemId) {
        return extenderService.get(itemId) != null;
    }

    public EquippedSlot getSlotByName(EquippedShip ship, String slotName) {
        if (slotName.contains(DEFENSE_SLOT_NAME)) {
            return slotDao.getById(ship.getDefenseSlotId());
        } else if (slotName.contains(WEAPON_SLOT_NAME)) {
            return slotDao.getById(ship.getWeaponSlotId());
        } else {
            throw new BadSlotNameException(slotName);
        }
    }
}