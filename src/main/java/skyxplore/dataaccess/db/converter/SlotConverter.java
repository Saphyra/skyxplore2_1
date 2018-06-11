package skyxplore.dataaccess.db.converter;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import skyxplore.dataaccess.db.entity.SlotEntity;
import skyxplore.service.domain.EquippedSlot;

@Component
@RequiredArgsConstructor
public class SlotConverter {

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
