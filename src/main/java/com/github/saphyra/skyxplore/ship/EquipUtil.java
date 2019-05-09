package com.github.saphyra.skyxplore.ship;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.github.saphyra.skyxplore.common.exception.BadSlotNameException;
import com.github.saphyra.skyxplore.gamedata.subservice.ExtenderService;
import com.github.saphyra.skyxplore.ship.domain.EquippedShip;
import com.github.saphyra.skyxplore.slot.SlotQueryService;
import com.github.saphyra.skyxplore.slot.domain.EquippedSlot;
import org.springframework.stereotype.Component;

import static com.github.saphyra.skyxplore.ship.EquippedShipConstants.DEFENSE_SLOT_NAME;
import static com.github.saphyra.skyxplore.ship.EquippedShipConstants.WEAPON_SLOT_NAME;

@Component
@Slf4j
@RequiredArgsConstructor
class EquipUtil {
    private final ExtenderService extenderService;
    private final SlotQueryService slotDao;

    boolean isExtender(String itemId) {
        return extenderService.get(itemId) != null;
    }

    EquippedSlot getSlotByName(EquippedShip ship, String slotName) {
        if (slotName.contains(DEFENSE_SLOT_NAME)) {
            return slotDao.findSlotById(ship.getDefenseSlotId());
        } else if (slotName.contains(WEAPON_SLOT_NAME)) {
            return slotDao.findSlotById(ship.getWeaponSlotId());
        } else {
            throw new BadSlotNameException(slotName);
        }
    }
}