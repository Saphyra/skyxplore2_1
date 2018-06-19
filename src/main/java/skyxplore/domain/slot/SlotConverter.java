package skyxplore.domain.slot;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import skyxplore.domain.ConverterBase;

@Component
@RequiredArgsConstructor
public class SlotConverter extends ConverterBase<SlotEntity, EquippedSlot> {

    @Override
    public SlotEntity convertDomain(EquippedSlot domain) {
        SlotEntity entity = new SlotEntity();
        entity.setSlotId(domain.getSlotId());
        entity.setShipId(domain.getShipId());
        entity.setFrontSlot(domain.getFrontSlot());
        entity.setFrontEquipped(domain.getFrontEquipped());
        entity.setLeftSlot(domain.getLeftSlot());
        entity.setLeftEquipped(domain.getLeftEquipped());
        entity.setRightSlot(domain.getRightSlot());
        entity.setRightEquipped(domain.getRightEquipped());
        entity.setBackSlot(domain.getBackSlot());
        entity.setBackEquipped(domain.getBackEquipped());
        return entity;
    }

    @Override
    public EquippedSlot convertEntity(SlotEntity entity) {
        EquippedSlot domain = new EquippedSlot();
        domain.setSlotId(entity.getSlotId());
        domain.setShipId(entity.getShipId());
        domain.setFrontSlot(entity.getFrontSlot());
        domain.setFrontEquipped(entity.getFrontEquipped());
        domain.setLeftSlot(entity.getLeftSlot());
        domain.setLeftEquipped(entity.getLeftEquipped());
        domain.setRightSlot(entity.getRightSlot());
        domain.setRightEquipped(entity.getRightEquipped());
        domain.setBackSlot(entity.getBackSlot());
        domain.setBackEquipped(entity.getBackEquipped());
        return domain;
    }
}
