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
        Slot slot = getSlot();
        EquippedSlot equippedSlot = EquippedSlot.builder()
            .slotId(idGenerator.generateRandomId())
            .shipId(shipId)
            .frontSlot(slot.getFront())
            .leftSlot(slot.getSide())
            .rightSlot(slot.getSide())
            .backSlot(slot.getBack())
            .build();
        fillSlotWithEquipments(equippedSlot);

        slotDao.save(equippedSlot);
        log.info("Slot created: {}", equippedSlot);
        return equippedSlot.getSlotId();
    }

    abstract Slot getSlot();

    abstract void fillSlotWithEquipments(EquippedSlot equippedSlot);
}
