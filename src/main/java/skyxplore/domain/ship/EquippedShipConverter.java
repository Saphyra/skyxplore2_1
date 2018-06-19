package skyxplore.domain.ship;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import skyxplore.domain.ConverterBase;
import skyxplore.domain.slot.SlotConverter;

@Component
@RequiredArgsConstructor
@Slf4j
public class EquippedShipConverter extends ConverterBase<EquippedShipEntity, EquippedShip> {
    private final SlotConverter slotConverter;

    @Override
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

    @Override
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
