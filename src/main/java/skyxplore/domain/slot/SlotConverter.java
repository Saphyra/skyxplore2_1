package skyxplore.domain.slot;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import skyxplore.domain.ConverterBase;
import skyxplore.encryption.IntegerEncryptor;
import skyxplore.encryption.StringEncryptor;

import java.io.IOException;
import java.util.ArrayList;

@Component
@RequiredArgsConstructor
@SuppressWarnings("unchecked")
//TODO unit test
public class SlotConverter extends ConverterBase<SlotEntity, EquippedSlot> {
    private final IntegerEncryptor integerEncryptor;
    private final ObjectMapper objectMapper;
    private final StringEncryptor stringEncryptor;

    @Override
    public SlotEntity convertDomain(EquippedSlot domain) {
        if (domain == null) {
            throw new IllegalArgumentException("domain must not be null.");
        }
        SlotEntity entity = new SlotEntity();
        try {
            entity.setSlotId(domain.getSlotId());
            entity.setShipId(domain.getShipId());
            entity.setFrontSlot(integerEncryptor.encrypt(domain.getFrontSlot(), domain.getSlotId()));
            entity.setFrontEquipped(
                stringEncryptor.encryptEntity(
                    objectMapper.writeValueAsString(domain.getFrontEquipped()),
                    domain.getSlotId()
                )
            );
            entity.setLeftSlot(integerEncryptor.encrypt(domain.getLeftSlot(), domain.getSlotId()));
            entity.setLeftEquipped(
                stringEncryptor.encryptEntity(
                    objectMapper.writeValueAsString(domain.getLeftEquipped()),
                    domain.getSlotId()
                )
            );
            entity.setRightSlot(integerEncryptor.encrypt(domain.getRightSlot(), domain.getSlotId()));
            entity.setRightEquipped(
                stringEncryptor.encryptEntity(
                    objectMapper.writeValueAsString(domain.getRightEquipped()),
                    domain.getSlotId()
                )
            );
            entity.setBackSlot(integerEncryptor.encrypt(domain.getBackSlot(), domain.getSlotId()));
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
    public EquippedSlot convertEntity(SlotEntity entity) {
        EquippedSlot domain = new EquippedSlot();
        try {
            domain.setSlotId(entity.getSlotId());
            domain.setShipId(entity.getShipId());
            domain.setFrontSlot(integerEncryptor.decrypt(entity.getFrontSlot(), entity.getSlotId()));
            domain.addFrontAll(
                objectMapper.readValue(
                    stringEncryptor.decryptEntity(
                        entity.getFrontEquipped(),
                        entity.getSlotId()
                    ),
                    ArrayList.class
                )
            );
            domain.setLeftSlot(integerEncryptor.decrypt(entity.getLeftSlot(), entity.getSlotId()));
            domain.addLeftAll(
                objectMapper.readValue(
                    stringEncryptor.decryptEntity(
                        entity.getLeftEquipped(),
                        entity.getSlotId()
                    ),
                    ArrayList.class
                )
            );
            domain.setRightSlot(integerEncryptor.decrypt(entity.getRightSlot(), entity.getSlotId()));
            domain.addRightAll(
                objectMapper.readValue(
                    stringEncryptor.decryptEntity(
                        entity.getRightEquipped(),
                        entity.getSlotId()
                    ),
                    ArrayList.class)
            );
            domain.setBackSlot(integerEncryptor.decrypt(entity.getBackSlot(), entity.getSlotId()));
            domain.addBackAll(
                objectMapper.readValue(
                    stringEncryptor.decryptEntity(
                        entity.getBackEquipped(),
                        entity.getSlotId()
                    ),
                    ArrayList.class
                )
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return domain;
    }
}
