package skyxplore.restcontroller.view.converter;

import org.springframework.stereotype.Component;
import skyxplore.restcontroller.view.SlotView;
import skyxplore.service.domain.EquippedSlot;

@Component
public class SlotViewConverter {
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
