package skyxplore.restcontroller.view.converter;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import skyxplore.dataaccess.gamedata.subservice.ShipService;
import skyxplore.restcontroller.view.ShipView;
import skyxplore.service.domain.EquippedShip;
import skyxplore.service.domain.EquippedSlot;

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
