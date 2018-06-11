package skyxplore.dataaccess.gamedata;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import skyxplore.dataaccess.gamedata.entity.Ship;
import skyxplore.dataaccess.gamedata.entity.abstractentity.GeneralDescription;
import skyxplore.dataaccess.gamedata.subservice.*;
import skyxplore.exception.EquipmentNotFoundException;
import skyxplore.service.domain.EquippedShip;
import skyxplore.service.domain.EquippedSlot;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class GameDataService {
    private final AbilityService abilityService;
    private final ArmorService armorService;
    private final BatteryService batteryService;
    private final CoreHullService coreHullService;
    private final ExtenderService extenderService;
    private final GeneratorService generatorService;
    private final MaterialService materialService;
    private final ShieldService shieldService;
    private final ShipService shipService;
    private final StorageService storageService;
    private final WeaponService weaponService;

    public Ship getShip(String id) {
        return shipService.get(id);
    }

    public Map<String, GeneralDescription> collectEquipmentData(EquippedShip ship, EquippedSlot defenseSlot, EquippedSlot weaponShot) {
        Map<String, GeneralDescription> result = new HashMap<>();
        result.put(ship.getShipType(), getShip(ship.getShipType()));
        result.putAll(getData(ship.getConnectorEquipped()));
        result.putAll(getData(defenseSlot));
        result.putAll(getData(weaponShot));
        result.putAll(getData(getShip(ship.getShipType()).getAbility()));
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
        ids.forEach(e -> result.put(e, getData(e)));
        return result;
    }

    private GeneralDescription getData(String id) {
        GeneralDescription result = null;
        if (result == null) {
            result = abilityService.get(id);
        }if (result == null) {
            result = armorService.get(id);
        }if (result == null) {
            result = batteryService.get(id);
        }if (result == null) {
            result = coreHullService.get(id);
        }if (result == null) {
            result = extenderService.get(id);
        }if (result == null) {
            result = generatorService.get(id);
        }if (result == null) {
            result = materialService.get(id);
        }if (result == null) {
            result = shieldService.get(id);
        }if (result == null) {
            result = shipService.get(id);
        }if (result == null) {
            result = storageService.get(id);
        }if (result == null) {
            result = weaponService.get(id);
        }if (result == null) {
            throw new EquipmentNotFoundException("Equipment not found with id " + id);
        }
        return result;
    }
}
