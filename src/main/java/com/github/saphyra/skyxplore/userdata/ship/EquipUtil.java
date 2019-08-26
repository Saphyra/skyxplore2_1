package com.github.saphyra.skyxplore.userdata.ship;

import com.github.saphyra.skyxplore.common.ExceptionFactory;
import com.github.saphyra.skyxplore.data.subservice.ExtenderService;
import com.github.saphyra.skyxplore.userdata.ship.domain.EquippedShip;
import com.github.saphyra.skyxplore.userdata.slot.SlotQueryService;
import com.github.saphyra.skyxplore.userdata.slot.domain.EquippedSlot;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import static com.github.saphyra.skyxplore.data.DataConstants.DEFENSE_SLOT_NAME;
import static com.github.saphyra.skyxplore.data.DataConstants.WEAPON_SLOT_NAME;

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
            return slotDao.findSlotByIdValidated(ship.getDefenseSlotId());
        } else if (slotName.contains(WEAPON_SLOT_NAME)) {
            return slotDao.findSlotByIdValidated(ship.getWeaponSlotId());
        } else {
            throw ExceptionFactory.invalidSlotName(slotName);
        }
    }
}
