package skyxplore.domain.ship;

import java.io.IOException;
import java.util.ArrayList;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import skyxplore.domain.ConverterBase;
import skyxplore.encryption.IntegerEncryptor;
import skyxplore.encryption.StringEncryptor;

@Component
@RequiredArgsConstructor
@Slf4j
@SuppressWarnings("unchecked")
public class EquippedShipConverter extends ConverterBase<EquippedShipEntity, EquippedShip> {
    private final IntegerEncryptor integerEncryptor;
    private final ObjectMapper objectMapper;
    private final StringEncryptor stringEncryptor;

    @Override
    public EquippedShipEntity convertDomain(EquippedShip domain) {
        if (domain == null) {
            throw new IllegalArgumentException("domain must not be null.");
        }
        EquippedShipEntity entity = new EquippedShipEntity();
        try {
            entity.setCharacterId(domain.getCharacterId());
            entity.setShipId(domain.getShipId());
            entity.setShipType(stringEncryptor.encryptEntity(domain.getShipType(), domain.getShipId()));
            entity.setCoreHull(integerEncryptor.encrypt(domain.getCoreHull(), domain.getShipId()));
            entity.setConnectorSlot(integerEncryptor.encrypt(domain.getConnectorSlot(), domain.getShipId()));
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
    public EquippedShip convertEntity(EquippedShipEntity entity) {
        if (entity == null) {
            return null;
        }
        EquippedShip domain = new EquippedShip();
        try {
            domain.setCharacterId(entity.getCharacterId());
            domain.setShipId(entity.getShipId());
            domain.setShipType(stringEncryptor.decryptEntity(entity.getShipType(), entity.getShipId()));
            domain.setCoreHull(integerEncryptor.decrypt(entity.getCoreHull(), entity.getShipId()));
            domain.setConnectorSlot(integerEncryptor.decrypt(entity.getConnectorSlot(), entity.getShipId()));
            domain.addConnectors(objectMapper.readValue(
                stringEncryptor.decryptEntity(
                    entity.getConnectorEquipped(),
                    entity.getShipId()
                ),
                ArrayList.class)
            );
            domain.setDefenseSlotId(entity.getDefenseSlotId());
            domain.setWeaponSlotId(entity.getWeaponSlotId());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return domain;
    }
}
