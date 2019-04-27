package skyxplore.domain.slot;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.saphyra.converter.ConverterBase;
import com.github.saphyra.encryption.impl.IntegerEncryptor;
import com.github.saphyra.encryption.impl.StringEncryptor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
public class SlotConverter extends ConverterBase<SlotEntity, EquippedSlot> {
    private final IntegerEncryptor integerEncryptor;
    private final ObjectMapper objectMapper;
    private final StringEncryptor stringEncryptor;

    @Override
    public SlotEntity processDomainConversion(EquippedSlot domain) {
        if (domain == null) {
            throw new IllegalArgumentException("domain must not be null.");
        }
        SlotEntity entity = new SlotEntity();
        try {
            entity.setSlotId(domain.getSlotId());
            entity.setShipId(domain.getShipId());
            entity.setFrontSlot(integerEncryptor.encryptEntity(domain.getFrontSlot(), domain.getSlotId()));
            entity.setFrontEquipped(
                stringEncryptor.encryptEntity(
                    objectMapper.writeValueAsString(domain.getFrontEquipped()),
                    domain.getSlotId()
                )
            );
            entity.setLeftSlot(integerEncryptor.encryptEntity(domain.getLeftSlot(), domain.getSlotId()));
            entity.setLeftEquipped(
                stringEncryptor.encryptEntity(
                    objectMapper.writeValueAsString(domain.getLeftEquipped()),
                    domain.getSlotId()
                )
            );
            entity.setRightSlot(integerEncryptor.encryptEntity(domain.getRightSlot(), domain.getSlotId()));
            entity.setRightEquipped(
                stringEncryptor.encryptEntity(
                    objectMapper.writeValueAsString(domain.getRightEquipped()),
                    domain.getSlotId()
                )
            );
            entity.setBackSlot(integerEncryptor.encryptEntity(domain.getBackSlot(), domain.getSlotId()));
            entity.setBackEquipped(
                stringEncryptor.encryptEntity(
                    objectMapper.writeValueAsString(domain.getBackEquipped()),
                    domain.getSlotId()
                )
            );
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return entity;
    }

    @Override
    public EquippedSlot processEntityConversion(SlotEntity entity) {
        if (entity == null) {
            return null;
        }

        EquippedSlot domain = new EquippedSlot();
        try {
            domain.setSlotId(entity.getSlotId());
            domain.setShipId(entity.getShipId());
            domain.setFrontSlot(integerEncryptor.decryptEntity(entity.getFrontSlot(), entity.getSlotId()));
            domain.addFront(getElements(entity.getSlotId(), entity.getFrontEquipped()));
            domain.setLeftSlot(integerEncryptor.decryptEntity(entity.getLeftSlot(), entity.getSlotId()));
            domain.addLeft(getElements(entity.getSlotId(), entity.getLeftEquipped()));
            domain.setRightSlot(integerEncryptor.decryptEntity(entity.getRightSlot(), entity.getSlotId()));
            domain.addRight(getElements(entity.getSlotId(), entity.getRightEquipped()));
            domain.setBackSlot(integerEncryptor.decryptEntity(entity.getBackSlot(), entity.getSlotId()));
            domain.addBack(getElements(entity.getSlotId(), entity.getBackEquipped()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return domain;
    }

    private List<String> getElements(String slotId, String frontEquipped) throws IOException {
        return Arrays.asList(objectMapper.readValue(
            stringEncryptor.decryptEntity(
                frontEquipped,
                slotId
            ),
            String[].class
        ));
    }
}
