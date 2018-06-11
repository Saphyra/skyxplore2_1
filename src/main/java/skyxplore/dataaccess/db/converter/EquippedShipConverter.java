package skyxplore.dataaccess.db.converter;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import skyxplore.dataaccess.db.entity.EquippedShipEntity;
import skyxplore.service.domain.EquippedShip;

@Component
@RequiredArgsConstructor
@Slf4j
public class EquippedShipConverter {
    private final SlotConverter slotConverter;

    public EquippedShipEntity convertDomain(EquippedShip domain){
        EquippedShipEntity entity = new EquippedShipEntity();
        entity.setCharacterId(domain.getCharacterId());
        entity.setShipId(domain.getShipId());
        entity.setShipType(domain.getShipType());
        entity.setCoreHull(domain.getCoreHull());
        entity.setConnectorSlot(domain.getConnectorSlot());
        entity.setConnectorEquipped(domain.getConnectorEquipped());
        entity.setDefenseSlotId(domain.getDefenseSlotId());
        entity.setWeaponSlotId(domain.getWeaponSlotId());
        return entity;
    }

    public EquippedShip convertEntity(EquippedShipEntity entity){
        EquippedShip domain = new EquippedShip();
        domain.setCharacterId(entity.getCharacterId());
        domain.setShipId(entity.getShipId());
        domain.setShipType(entity.getShipType());
        domain.setCoreHull(entity.getCoreHull());
        domain.setConnectorSlot(entity.getConnectorSlot());
        domain.setConnectorEquipped(entity.getConnectorEquipped());
        domain.setDefenseSlotId(entity.getDefenseSlotId());
        domain.setWeaponSlotId(entity.getWeaponSlotId());
        return domain;
    }
}
