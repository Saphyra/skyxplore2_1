package org.github.saphyra.skyxplore.ship;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.github.saphyra.skyxplore.common.exception.BadSlotNameException;
import org.github.saphyra.skyxplore.ship.domain.EquipRequest;
import org.github.saphyra.skyxplore.ship.domain.EquippedShip;
import org.github.saphyra.skyxplore.slot.domain.EquippedSlot;
import org.github.saphyra.skyxplore.slot.repository.SlotDao;
import org.springframework.stereotype.Service;

import static org.github.saphyra.skyxplore.ship.EquippedShipConstants.BACK_SLOT_NAME;
import static org.github.saphyra.skyxplore.ship.EquippedShipConstants.FRONT_SLOT_NAME;
import static org.github.saphyra.skyxplore.ship.EquippedShipConstants.LEFT_SLOT_NAME;
import static org.github.saphyra.skyxplore.ship.EquippedShipConstants.RIGHT_SLOT_NAME;

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
        if (request.getEquipTo().contains(FRONT_SLOT_NAME)) {
            slot.addFront(request.getItemId());
        } else if (request.getEquipTo().contains(BACK_SLOT_NAME)) {
            slot.addBack(request.getItemId());
        } else if (request.getEquipTo().contains(LEFT_SLOT_NAME)) {
            slot.addLeft(request.getItemId());
        } else if (request.getEquipTo().contains(RIGHT_SLOT_NAME)) {
            slot.addRight(request.getItemId());
        } else {
            throw new BadSlotNameException(request.getEquipTo());
        }
    }
}
