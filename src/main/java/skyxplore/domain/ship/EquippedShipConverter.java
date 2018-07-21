package skyxplore.domain.ship;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import skyxplore.domain.ConverterBase;

import java.io.IOException;
import java.util.ArrayList;

@Component
@RequiredArgsConstructor
@Slf4j
@SuppressWarnings("unchecked")
public class EquippedShipConverter extends ConverterBase<EquippedShipEntity, EquippedShip> {
    private final ObjectMapper objectMapper;

    @Override
    public EquippedShipEntity convertDomain(EquippedShip domain) {
        EquippedShipEntity entity = new EquippedShipEntity();
        try {
            entity.setCharacterId(domain.getCharacterId());
            entity.setShipId(domain.getShipId());
            entity.setShipType(domain.getShipType());
            entity.setCoreHull(domain.getCoreHull());
            entity.setConnectorSlot(domain.getConnectorSlot());
            entity.setConnectorEquipped(objectMapper.writeValueAsString(domain.getConnectorEquipped()));
            entity.setDefenseSlotId(domain.getDefenseSlotId());
            entity.setWeaponSlotId(domain.getWeaponSlotId());
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return entity;
    }

    @Override
    public EquippedShip convertEntity(EquippedShipEntity entity) {
        EquippedShip domain = new EquippedShip();
        try {
            domain.setCharacterId(entity.getCharacterId());
            domain.setShipId(entity.getShipId());
            domain.setShipType(entity.getShipType());
            domain.setCoreHull(entity.getCoreHull());
            domain.setConnectorSlot(entity.getConnectorSlot());
            domain.addConnectors(objectMapper.readValue(entity.getConnectorEquipped(), ArrayList.class));
            domain.setDefenseSlotId(entity.getDefenseSlotId());
            domain.setWeaponSlotId(entity.getWeaponSlotId());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return domain;
    }
}
