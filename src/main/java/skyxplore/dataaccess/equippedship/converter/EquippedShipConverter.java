package skyxplore.dataaccess.equippedship.converter;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import skyxplore.dataaccess.equippedship.entity.EquippedShipEntity;
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
        entity.setDefenseSlot(slotConverter.convertDomain(domain.getDefenseSlot()));
        entity.setWeaponSlot(slotConverter.convertDomain(domain.getWeaponSlot()));
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
        domain.setDefenseSlot(slotConverter.convertEntity(entity.getDefenseSlot()));
        domain.setWeaponSlot(slotConverter.convertEntity(entity.getWeaponSlot()));
        return domain;
    }
}
