package org.github.saphyra.skyxplore.slot;

import lombok.RequiredArgsConstructor;
import org.github.saphyra.skyxplore.common.exception.EquippedSlotNotFoundException;
import org.github.saphyra.skyxplore.slot.domain.EquippedSlot;
import org.github.saphyra.skyxplore.slot.repository.SlotDao;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SlotQueryService {
    private final SlotDao slotDao;

    public EquippedSlot findSlotById(String slotId) {
        return slotDao.findById(slotId)
            .orElseThrow(() -> new EquippedSlotNotFoundException("EquippedSlot not found with slotId " + slotId));
    }
}
