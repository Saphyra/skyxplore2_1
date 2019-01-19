package skyxplore.service.gamedata;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import skyxplore.dataaccess.gamedata.entity.Ship;
import skyxplore.dataaccess.gamedata.entity.abstractentity.GeneralDescription;
import skyxplore.dataaccess.gamedata.subservice.ShipService;
import skyxplore.domain.ship.EquippedShip;
import skyxplore.domain.slot.EquippedSlot;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EquipmentDataCollectorService {
    private final DataQueryService dataQueryService;
    private final ShipService shipService;


    public Map<String, GeneralDescription> collectEquipmentData(List<String> ids) {
        return ids.stream()
            .map(dataQueryService::getData)
            .collect(Collectors.toMap(GeneralDescription::getId, g -> g, (k1, k2) -> k1));
    }

    public Map<String, GeneralDescription> collectEquipmentData(EquippedShip ship, EquippedSlot defenseSlot, EquippedSlot weaponSlot) {
        Map<String, GeneralDescription> result = new HashMap<>();
        Ship shipEntity = shipService.get(ship.getShipType());
        result.put(ship.getShipType(), shipEntity);
        result.putAll(getData(ship.getConnectorEquipped()));
        result.putAll(getData(defenseSlot));
        result.putAll(getData(weaponSlot));
        result.putAll(getData(shipEntity.getAbility()));
        return result;
    }

    private Map<String, GeneralDescription> getData(EquippedSlot slot) {
        Map<String, GeneralDescription> result = new HashMap<>();
        result.putAll(getData(slot.getFrontEquipped()));
        result.putAll(getData(slot.getLeftEquipped()));
        result.putAll(getData(slot.getRightEquipped()));
        result.putAll(getData(slot.getBackEquipped()));
        return result;
    }

    private Map<String, GeneralDescription> getData(Collection<String> ids) {
        Map<String, GeneralDescription> result = new HashMap<>();
        ids.forEach(e -> result.put(e, dataQueryService.getData(e)));
        return result;
    }
}
