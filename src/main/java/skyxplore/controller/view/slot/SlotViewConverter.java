package skyxplore.controller.view.slot;

import org.springframework.stereotype.Component;
import skyxplore.domain.slot.EquippedSlot;
import skyxplore.controller.view.AbstractViewConverter;

@Component
public class SlotViewConverter extends AbstractViewConverter<EquippedSlot, SlotView> {
    public SlotView convertDomain(EquippedSlot domain){
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
