package org.github.saphyra.skyxplore.ship;

import org.github.saphyra.skyxplore.ship.domain.SlotView;
import org.github.saphyra.skyxplore.slot.domain.EquippedSlot;
import org.springframework.stereotype.Component;

import org.github.saphyra.skyxplore.common.AbstractViewConverter;

@Component
class SlotViewConverter extends AbstractViewConverter<EquippedSlot, SlotView> {
    public SlotView convertDomain(EquippedSlot domain) {
        return SlotView.builder()
            .frontSlot(domain.getFrontSlot())
            .frontEquipped(domain.getFrontEquipped())
            .rightSlot(domain.getRightSlot())
            .rightEquipped(domain.getRightEquipped())
            .backSlot(domain.getBackSlot())
            .backEquipped(domain.getBackEquipped())
            .leftSlot(domain.getLeftSlot())
            .leftEquipped(domain.getLeftEquipped())
            .build();
    }
}
