package com.github.saphyra.skyxplore.userdata.ship;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.github.saphyra.skyxplore.common.exception.BadSlotNameException;
import com.github.saphyra.skyxplore.userdata.ship.domain.EquipRequest;
import com.github.saphyra.skyxplore.userdata.ship.domain.EquippedShip;
import com.github.saphyra.skyxplore.userdata.slot.domain.EquippedSlot;
import com.github.saphyra.skyxplore.userdata.slot.repository.SlotDao;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
class EquipToSlotService {
    private final EquipUtil equipUtil;
    private final SlotDao slotDao;

    void equipToSlot(EquipRequest request, EquippedShip ship) {
        EquippedSlot slot = equipUtil.getSlotByName(ship, request.getEquipTo());
        addElementToSlot(slot, request);
        slotDao.save(slot);
    }

    private void addElementToSlot(EquippedSlot slot, EquipRequest request) {
        if (request.getEquipTo().contains(EquippedShipConstants.FRONT_SLOT_NAME)) {
            slot.addFront(request.getItemId());
        } else if (request.getEquipTo().contains(EquippedShipConstants.BACK_SLOT_NAME)) {
            slot.addBack(request.getItemId());
        } else if (request.getEquipTo().contains(EquippedShipConstants.LEFT_SLOT_NAME)) {
            slot.addLeft(request.getItemId());
        } else if (request.getEquipTo().contains(EquippedShipConstants.RIGHT_SLOT_NAME)) {
            slot.addRight(request.getItemId());
        } else {
            throw new BadSlotNameException(request.getEquipTo());
        }
    }
}
