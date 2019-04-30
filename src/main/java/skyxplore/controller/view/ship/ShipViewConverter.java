package skyxplore.controller.view.ship;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.github.saphyra.skyxplore.gamedata.subservice.ShipService;
import org.github.saphyra.skyxplore.ship.domain.EquippedShip;
import org.github.saphyra.skyxplore.slot.domain.EquippedSlot;
import skyxplore.controller.view.slot.SlotViewConverter;

@Component
@RequiredArgsConstructor
public class ShipViewConverter {
    private final SlotViewConverter slotViewConverter;
    private final ShipService shipService;

    public ShipView convertDomain(EquippedShip domain, EquippedSlot defenseSlot, EquippedSlot weaponSlot) {
        ShipView view = new ShipView();
        view.setShipType(domain.getShipType());
        view.setCoreHull(domain.getCoreHull());
        view.setConnectorSlot(domain.getConnectorSlot());
        view.setConnectorEquipped(domain.getConnectorEquipped());
        view.setDefenseSlot(slotViewConverter.convertDomain(defenseSlot));
        view.setWeaponSlot(slotViewConverter.convertDomain(weaponSlot));
        view.setAbility(shipService.get(domain.getShipType()).getAbility());
        return view;
    }
}
