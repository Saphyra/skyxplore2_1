package com.github.saphyra.skyxplore.ship;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.github.saphyra.skyxplore.common.exception.BadSlotNameException;
import com.github.saphyra.skyxplore.ship.domain.EquippedShip;
import com.github.saphyra.skyxplore.ship.domain.UnequipRequest;
import com.github.saphyra.skyxplore.slot.domain.EquippedSlot;
import com.github.saphyra.skyxplore.slot.repository.SlotDao;
import org.springframework.stereotype.Service;

import static com.github.saphyra.skyxplore.ship.EquippedShipConstants.BACK_SLOT_NAME;
import static com.github.saphyra.skyxplore.ship.EquippedShipConstants.FRONT_SLOT_NAME;
import static com.github.saphyra.skyxplore.ship.EquippedShipConstants.LEFT_SLOT_NAME;
import static com.github.saphyra.skyxplore.ship.EquippedShipConstants.RIGHT_SLOT_NAME;

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
            throw new BadSlotNameException(request.getSlot());
        }
    }
}
