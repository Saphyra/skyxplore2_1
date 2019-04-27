package skyxplore.domain.ship;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.saphyra.converter.ConverterBase;
import com.github.saphyra.encryption.impl.IntegerEncryptor;
import com.github.saphyra.encryption.impl.StringEncryptor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Arrays;

@Component
@RequiredArgsConstructor
@Slf4j
public class EquippedShipConverter extends ConverterBase<EquippedShipEntity, EquippedShip> {
    private final IntegerEncryptor integerEncryptor;
    private final ObjectMapper objectMapper;
    private final StringEncryptor stringEncryptor;

    @Override
    public EquippedShipEntity processDomainConversion(EquippedShip domain) {
        if (domain == null) {
            throw new IllegalArgumentException("domain must not be null.");
        }
        EquippedShipEntity entity = new EquippedShipEntity();
        try {
            entity.setCharacterId(domain.getCharacterId());
            entity.setShipId(domain.getShipId());
            entity.setShipType(stringEncryptor.encryptEntity(domain.getShipType(), domain.getShipId()));
            entity.setCoreHull(integerEncryptor.encryptEntity(domain.getCoreHull(), domain.getShipId()));
            entity.setConnectorSlot(integerEncryptor.encryptEntity(domain.getConnectorSlot(), domain.getShipId()));
            entity.setConnectorEquipped(stringEncryptor.encryptEntity(
                objectMapper.writeValueAsString(domain.getConnectorEquipped()),
                domain.getShipId())
            );
            entity.setDefenseSlotId(domain.getDefenseSlotId());
            entity.setWeaponSlotId(domain.getWeaponSlotId());
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return entity;
    }

    @Override
    public EquippedShip processEntityConversion(EquippedShipEntity entity) {
        if (entity == null) {
            return null;
        }
        EquippedShip domain = new EquippedShip();
        try {
            domain.setCharacterId(entity.getCharacterId());
            domain.setShipId(entity.getShipId());
            domain.setShipType(stringEncryptor.decryptEntity(entity.getShipType(), entity.getShipId()));
            domain.setCoreHull(integerEncryptor.decryptEntity(entity.getCoreHull(), entity.getShipId()));
            domain.setConnectorSlot(integerEncryptor.decryptEntity(entity.getConnectorSlot(), entity.getShipId()));
            domain.addConnectors(Arrays.asList(objectMapper.readValue(
                stringEncryptor.decryptEntity(
                    entity.getConnectorEquipped(),
                    entity.getShipId()
                ),
                String[].class)
            ));
            domain.setDefenseSlotId(entity.getDefenseSlotId());
            domain.setWeaponSlotId(entity.getWeaponSlotId());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return domain;
    }
}
