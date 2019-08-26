package com.github.saphyra.skyxplore.userdata.ship;

import com.github.saphyra.skyxplore.common.ExceptionFactory;
import com.github.saphyra.skyxplore.userdata.ship.domain.EquippedShip;
import com.github.saphyra.skyxplore.userdata.ship.domain.UnequipRequest;
import com.github.saphyra.skyxplore.userdata.slot.domain.EquippedSlot;
import com.github.saphyra.skyxplore.userdata.slot.repository.SlotDao;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static com.github.saphyra.skyxplore.userdata.ship.EquippedShipConstants.BACK_SLOT_NAME;
import static com.github.saphyra.skyxplore.userdata.ship.EquippedShipConstants.FRONT_SLOT_NAME;
import static com.github.saphyra.skyxplore.userdata.ship.EquippedShipConstants.LEFT_SLOT_NAME;
import static com.github.saphyra.skyxplore.userdata.ship.EquippedShipConstants.RIGHT_SLOT_NAME;

@Service
@Slf4j
@RequiredArgsConstructor
class UnequipFromSlotService {
    private final EquipUtil equipUtil;
    private final SlotDao slotDao;

    void unequipFromSlot(UnequipRequest request, EquippedShip ship) {
        EquippedSlot slot = equipUtil.getSlotByName(ship, request.getSlot());
        removeElementFromSlot(slot, request);
        slotDao.save(slot);
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
            throw ExceptionFactory.invalidSlotName(request.getSlot());
        }
    }
}
