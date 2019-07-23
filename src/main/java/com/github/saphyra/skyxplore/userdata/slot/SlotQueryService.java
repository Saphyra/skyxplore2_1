package com.github.saphyra.skyxplore.userdata.slot;

import com.github.saphyra.skyxplore.userdata.slot.domain.EquippedSlot;
import lombok.RequiredArgsConstructor;
import com.github.saphyra.skyxplore.common.exception.EquippedSlotNotFoundException;
import com.github.saphyra.skyxplore.userdata.slot.repository.SlotDao;
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
