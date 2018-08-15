package skyxplore.controller.view.slot;

import org.springframework.stereotype.Component;
import skyxplore.domain.slot.EquippedSlot;
import skyxplore.controller.view.AbstractViewConverter;

@Component
public class SlotViewConverter extends AbstractViewConverter<EquippedSlot, SlotView> {
    public SlotView convertDomain(EquippedSlot domain){
        SlotView view = new SlotView();
        view.setSlotId(domain.getSlotId());
        view.setShipId(domain.getShipId());
        view.setFrontSlot(domain.getFrontSlot());
        view.setFrontEquipped(domain.getFrontEquipped());
        view.setLeftSlot(domain.getLeftSlot());
        view.setLeftEquipped(domain.getLeftEquipped());
        view.setRightSlot(domain.getRightSlot());
        view.setRightEquipped(domain.getRightEquipped());
        view.setBackSlot(domain.getBackSlot());
        view.setBackEquipped(domain.getBackEquipped());
        return view;
    }
}
