package skyxplore.restcontroller.view.ship;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import skyxplore.dataaccess.gamedata.subservice.ShipService;
import skyxplore.domain.ship.EquippedShip;
import skyxplore.domain.slot.EquippedSlot;
import skyxplore.restcontroller.view.slot.SlotViewConverter;

@Component
@RequiredArgsConstructor
public class ShipViewConverter {
    private final SlotViewConverter slotViewConverter;
    private final ShipService shipService;

    public ShipView convertDomain(EquippedShip domain, EquippedSlot defennseSlot, EquippedSlot weaponSlot){
        ShipView view = new ShipView();
        view.setShipId(domain.getShipId());
        view.setCharacterId(domain.getCharacterId());
        view.setShipType(domain.getShipType());
        view.setCoreHull(domain.getCoreHull());
        view.setConnectorSlot(domain.getConnectorSlot());
        view.setConnectorEquipped(domain.getConnectorEquipped());
        view.setDefenseSlot(slotViewConverter.convertDomain(defennseSlot));
        view.setWeaponSlot(slotViewConverter.convertDomain(weaponSlot));
        view.setAbility(shipService.get(domain.getShipType()).getAbility());
        return view;
    }
}
