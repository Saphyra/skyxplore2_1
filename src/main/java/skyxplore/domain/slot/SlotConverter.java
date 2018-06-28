package skyxplore.domain.slot;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import skyxplore.domain.ConverterBase;

import java.io.IOException;
import java.util.ArrayList;

@Component
@RequiredArgsConstructor
@SuppressWarnings("unchecked")
public class SlotConverter extends ConverterBase<SlotEntity, EquippedSlot> {
    private final ObjectMapper objectMapper;

    @Override
    public SlotEntity convertDomain(EquippedSlot domain) {
        SlotEntity entity = new SlotEntity();
        try {
            entity.setSlotId(domain.getSlotId());
            entity.setShipId(domain.getShipId());
            entity.setFrontSlot(domain.getFrontSlot());
            entity.setFrontEquipped(objectMapper.writeValueAsString(domain.getFrontEquipped()));
            entity.setLeftSlot(domain.getLeftSlot());
            entity.setLeftEquipped(objectMapper.writeValueAsString(domain.getLeftEquipped()));
            entity.setRightSlot(domain.getRightSlot());
            entity.setRightEquipped(objectMapper.writeValueAsString(domain.getRightEquipped()));
            entity.setBackSlot(domain.getBackSlot());
            entity.setBackEquipped(objectMapper.writeValueAsString(domain.getBackEquipped()));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return entity;
    }

    @Override
    public EquippedSlot convertEntity(SlotEntity entity) {
        EquippedSlot domain = new EquippedSlot();
        try {
            domain.setSlotId(entity.getSlotId());
            domain.setShipId(entity.getShipId());
            domain.setFrontSlot(entity.getFrontSlot());
            domain.setFrontEquipped(objectMapper.readValue(entity.getFrontEquipped(), ArrayList.class));
            domain.setLeftSlot(entity.getLeftSlot());
            domain.setLeftEquipped(objectMapper.readValue(entity.getLeftEquipped(), ArrayList.class));
            domain.setRightSlot(entity.getRightSlot());
            domain.setRightEquipped(objectMapper.readValue(entity.getRightEquipped(), ArrayList.class));
            domain.setBackSlot(entity.getBackSlot());
            domain.setBackEquipped(objectMapper.readValue(entity.getBackEquipped(), ArrayList.class));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return domain;
    }
}
