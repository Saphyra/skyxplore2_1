package skyxplore.restcontroller.view.converter;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import skyxplore.restcontroller.view.ShipView;
import skyxplore.service.domain.EquippedShip;

@Component
@RequiredArgsConstructor
public class ShipViewConverter {
    private final SlotViewConverter slotViewConverter;

    public ShipView convertDomain(EquippedShip domain){
        ShipView view = new ShipView();
        view.setShipId(domain.getShipId());
        view.setCharacterId(domain.getCharacterId());
        view.setShipType(domain.getShipType());
        view.setCoreHull(domain.getCoreHull());
        view.setConnectorSlot(domain.getConnectorSlot());
        view.setConnectorEquipped(domain.getConnectorEquipped());
        view.setDefenseSlot(slotViewConverter.convertDomain(domain.getDefenseSlot()));
        view.setWeaponSlot(slotViewConverter.convertDomain(domain.getWeaponSlot()));
        return view;
    }
}
