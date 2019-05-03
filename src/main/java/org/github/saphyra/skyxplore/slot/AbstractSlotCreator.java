package org.github.saphyra.skyxplore.slot;

import com.github.saphyra.util.IdGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.github.saphyra.skyxplore.gamedata.entity.Slot;
import org.github.saphyra.skyxplore.slot.domain.EquippedSlot;
import org.github.saphyra.skyxplore.slot.repository.SlotDao;

@RequiredArgsConstructor
@Slf4j
abstract class AbstractSlotCreator {
    private final IdGenerator idGenerator;
    private final SlotDao slotDao;

    String createSlot(String shipId) {
        //TODO use builder
        Slot slot = getSlot();
        EquippedSlot equippedSlot = EquippedSlot.builder().build();
        equippedSlot.setSlotId(idGenerator.generateRandomId());
        equippedSlot.setShipId(shipId);
        equippedSlot.setFrontSlot(slot.getFront());
        equippedSlot.setLeftSlot(slot.getSide());
        equippedSlot.setRightSlot(slot.getSide());
        equippedSlot.setBackSlot(slot.getBack());
        fillSlotWithEquipments(equippedSlot);

        slotDao.save(equippedSlot);
        log.info("Slot created: {}", equippedSlot);
        return equippedSlot.getSlotId();
    }

    abstract Slot getSlot();

    abstract void fillSlotWithEquipments(EquippedSlot equippedSlot);
}
