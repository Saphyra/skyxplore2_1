package skyxplore.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import skyxplore.dataaccess.gamedata.entity.Ship;
import skyxplore.dataaccess.gamedata.entity.abstractentity.GeneralDescription;
import skyxplore.dataaccess.gamedata.subservice.*;
import skyxplore.domain.ship.EquippedShip;
import skyxplore.domain.slot.EquippedSlot;
import skyxplore.exception.EquipmentNotFoundException;
import skyxplore.restcontroller.request.EquipmentCategoryRequest;

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

    public Map<String, GeneralDescription> getElementsOfCategory(EquipmentCategoryRequest category) {
        log.info("Querying equipments of category {}", category);
        Map<String, GeneralDescription> result = new HashMap<>();
        switch (category) {
            case ALL:
                result.putAll(batteryService);
                result.putAll(generatorService);
                result.putAll(extenderService);
                result.putAll(coreHullService);
                result.putAll(storageService);
                result.putAll(weaponService);
                result.putAll(armorService);
                result.putAll(shieldService);
                result.putAll(shipService);
                break;
            case CONNECTOR:
                result.putAll(batteryService);
                result.putAll(generatorService);
                result.putAll(extenderService);
                result.putAll(coreHullService);
                result.putAll(storageService);
                break;
            case ENERGY:
                result.putAll(batteryService);
                result.putAll(generatorService);
                break;
            case EXTENDER:
                result.putAll(extenderService);
                break;
            case COREHULL:
                result.putAll(coreHullService);
                break;
            case STORAGE:
                result.putAll(storageService);
                break;
            case DEFENSE:
                result.putAll(armorService);
                result.putAll(shieldService);
                break;
            case ARMOR:
                result.putAll(armorService);
                break;
            case SHIELD:
                result.putAll(shieldService);
                break;
            case SHIP:
                result.putAll(shipService);
                break;
            case WEAPON:
                result.putAll(weaponService);
                break;
        }
        return result;
    }

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
        }
        if (result == null) {
            result = armorService.get(id);
        }
        if (result == null) {
            result = batteryService.get(id);
        }
        if (result == null) {
            result = coreHullService.get(id);
        }
        if (result == null) {
            result = extenderService.get(id);
        }
        if (result == null) {
            result = generatorService.get(id);
        }
        if (result == null) {
            result = materialService.get(id);
        }
        if (result == null) {
            result = shieldService.get(id);
        }
        if (result == null) {
            result = shipService.get(id);
        }
        if (result == null) {
            result = storageService.get(id);
        }
        if (result == null) {
            result = weaponService.get(id);
        }
        if (result == null) {
            throw new EquipmentNotFoundException("Equipment not found with id " + id);
        }
        return result;
    }
}
