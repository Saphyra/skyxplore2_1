package org.github.saphyra.skyxplore.slot.repository;

import com.github.saphyra.converter.ConverterBase;
import com.github.saphyra.encryption.impl.IntegerEncryptor;
import com.github.saphyra.encryption.impl.StringEncryptor;
import lombok.RequiredArgsConstructor;
import org.github.saphyra.skyxplore.common.ObjectMapperDelegator;
import org.github.saphyra.skyxplore.slot.domain.EquippedSlot;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
class SlotConverter extends ConverterBase<SlotEntity, EquippedSlot> {
    private final IntegerEncryptor integerEncryptor;
    private final ObjectMapperDelegator objectMapperDelegator;
    private final StringEncryptor stringEncryptor;

    @Override
    public SlotEntity processDomainConversion(EquippedSlot domain) {
        return SlotEntity.builder()
            .slotId(domain.getSlotId())
            .shipId(domain.getShipId())
            .frontSlot(integerEncryptor.encryptEntity(domain.getFrontSlot(), domain.getSlotId()))
            .frontEquipped(stringEncryptor.encryptEntity(
                objectMapperDelegator.writeValueAsString(domain.getFrontEquipped()),
                domain.getSlotId()
            ))
            .leftSlot(integerEncryptor.encryptEntity(domain.getLeftSlot(), domain.getSlotId()))
            .leftEquipped(stringEncryptor.encryptEntity(
                objectMapperDelegator.writeValueAsString(domain.getLeftEquipped()),
                domain.getSlotId()
            ))
            .rightSlot(integerEncryptor.encryptEntity(domain.getRightSlot(), domain.getSlotId()))
            .rightEquipped(stringEncryptor.encryptEntity(
                objectMapperDelegator.writeValueAsString(domain.getRightEquipped()),
                domain.getSlotId()
            ))
            .backSlot(integerEncryptor.encryptEntity(domain.getBackSlot(), domain.getSlotId()))
            .backEquipped(stringEncryptor.encryptEntity(
                objectMapperDelegator.writeValueAsString(domain.getBackEquipped()),
                domain.getSlotId()
            ))
            .build();
    }

    @Override
    public EquippedSlot processEntityConversion(SlotEntity entity) {
        return EquippedSlot.builder()
            .slotId(entity.getSlotId())
            .shipId(entity.getShipId())
            .frontSlot(integerEncryptor.decryptEntity(entity.getFrontSlot(), entity.getSlotId()))
            .frontEquipped(getElements(entity.getSlotId(), entity.getFrontEquipped()))
            .leftSlot(integerEncryptor.decryptEntity(entity.getLeftSlot(), entity.getSlotId()))
            .leftEquipped(getElements(entity.getSlotId(), entity.getLeftEquipped()))
            .rightSlot(integerEncryptor.decryptEntity(entity.getRightSlot(), entity.getSlotId()))
            .rightEquipped(getElements(entity.getSlotId(), entity.getRightEquipped()))
            .backSlot(integerEncryptor.decryptEntity(entity.getBackSlot(), entity.getSlotId()))
            .backEquipped(getElements(entity.getSlotId(), entity.getBackEquipped()))
            .build();
    }

    private List<String> getElements(String slotId, String frontEquipped) {
        return objectMapperDelegator.readValue(
            stringEncryptor.decryptEntity(
                frontEquipped,
                slotId
            ),
            String[].class
        );
    }
}
