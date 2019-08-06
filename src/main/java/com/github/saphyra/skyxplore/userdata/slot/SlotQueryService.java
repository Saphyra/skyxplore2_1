package com.github.saphyra.skyxplore.userdata.slot;

import org.springframework.stereotype.Service;

import com.github.saphyra.skyxplore.common.exception.EquippedSlotNotFoundException;
import com.github.saphyra.skyxplore.userdata.slot.domain.EquippedSlot;
import com.github.saphyra.skyxplore.userdata.slot.repository.SlotDao;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SlotQueryService {
    private final SlotDao slotDao;

    public EquippedSlot findSlotByIdValidated(String slotId) {
        return slotDao.findById(slotId)
            .orElseThrow(() -> new EquippedSlotNotFoundException("EquippedSlot not found with slotId " + slotId));
    }
}
