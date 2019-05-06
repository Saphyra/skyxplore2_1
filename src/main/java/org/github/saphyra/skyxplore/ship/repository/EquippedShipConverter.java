package org.github.saphyra.skyxplore.ship.repository;

import com.github.saphyra.converter.ConverterBase;
import com.github.saphyra.encryption.impl.IntegerEncryptor;
import com.github.saphyra.encryption.impl.StringEncryptor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.github.saphyra.skyxplore.common.ObjectMapperDelegator;
import org.github.saphyra.skyxplore.ship.domain.EquippedShip;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
class EquippedShipConverter extends ConverterBase<EquippedShipEntity, EquippedShip> {
    private final IntegerEncryptor integerEncryptor;
    private final ObjectMapperDelegator objectMapperDelegator;
    private final StringEncryptor stringEncryptor;

    @Override
    public EquippedShipEntity processDomainConversion(EquippedShip domain) {
        return EquippedShipEntity.builder()
            .characterId(domain.getCharacterId())
            .shipId(domain.getShipId())
            .shipType(stringEncryptor.encryptEntity(domain.getShipType(), domain.getShipId()))
            .coreHull(integerEncryptor.encryptEntity(domain.getCoreHull(), domain.getShipId()))
            .connectorSlot(integerEncryptor.encryptEntity(domain.getConnectorSlot(), domain.getShipId()))
            .connectorEquipped(stringEncryptor.encryptEntity(
                objectMapperDelegator.writeValueAsString(domain.getConnectorEquipped()),
                domain.getShipId())
            )
            .defenseSlotId(domain.getDefenseSlotId())
            .weaponSlotId(domain.getWeaponSlotId())
            .build();
    }

    @Override
    public EquippedShip processEntityConversion(EquippedShipEntity entity) {
        List<String> connectors = objectMapperDelegator.readValue(
            stringEncryptor.decryptEntity(
                entity.getConnectorEquipped(),
                entity.getShipId()
            ),
            String[].class);
        return EquippedShip.builder()
            .characterId(entity.getCharacterId())
            .shipId(entity.getShipId())
            .shipType(stringEncryptor.decryptEntity(entity.getShipType(), entity.getShipId()))
            .coreHull(integerEncryptor.decryptEntity(entity.getCoreHull(), entity.getShipId()))
            .connectorSlot(integerEncryptor.decryptEntity(entity.getConnectorSlot(), entity.getShipId()))
            .connectorEquipped(connectors)
            .defenseSlotId(entity.getDefenseSlotId())
            .weaponSlotId(entity.getWeaponSlotId())
            .build();
    }
}
