package com.github.saphyra.skyxplore.userdata.slot;

import com.github.saphyra.skyxplore.common.ExceptionFactory;
import com.github.saphyra.skyxplore.userdata.slot.domain.EquippedSlot;
import com.github.saphyra.skyxplore.userdata.slot.repository.SlotDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SlotQueryService {
    private final SlotDao slotDao;

    public EquippedSlot findSlotByIdValidated(String slotId) {
        return slotDao.findById(slotId)
            .orElseThrow(() -> ExceptionFactory.equippedSlotNotFound(slotId));
    }
}
