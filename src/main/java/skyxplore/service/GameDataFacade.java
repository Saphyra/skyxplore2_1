package skyxplore.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import skyxplore.dataaccess.gamedata.entity.Ship;
import skyxplore.dataaccess.gamedata.entity.abstractentity.FactoryData;
import skyxplore.dataaccess.gamedata.entity.abstractentity.GeneralDescription;
import skyxplore.dataaccess.gamedata.entity.abstractentity.ShopData;
import skyxplore.dataaccess.gamedata.subservice.*;
import skyxplore.domain.ship.EquippedShip;
import skyxplore.domain.slot.EquippedSlot;
import skyxplore.exception.EquipmentNotFoundException;
import skyxplore.controller.request.EquipmentCategoryRequest;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@SuppressWarnings("WeakerAccess")
@Service
@RequiredArgsConstructor
@Slf4j
//TODO explode
public class GameDataFacade {
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

    public Map<String, GeneralDescription> collectEquipmentData(List<String> ids){
        return ids.stream().map(this::getData).collect(Collectors.toMap(GeneralDescription::getId, g -> g, (k1, k2) -> k1));
    }

    public Map<String, GeneralDescription> collectEquipmentData(EquippedShip ship, EquippedSlot defenseSlot, EquippedSlot weaponSlot) {
        Map<String, GeneralDescription> result = new HashMap<>();
        result.put(ship.getShipType(), getShip(ship.getShipType()));
        result.putAll(getData(ship.getConnectorEquipped()));
        result.putAll(getData(defenseSlot));
        result.putAll(getData(weaponSlot));
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

    public GeneralDescription getData(String id) {
        GeneralDescription result = abilityService.get(id);
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

    public FactoryData getFactoryData(String elementId){
        GeneralDescription element = getData(elementId);
        if(element instanceof FactoryData){
            return (FactoryData) element;
        }
        throw new IllegalArgumentException(elementId + " is not instance of FactoryData");
    }

    public ShopData findBuyable(String elementId){
        GeneralDescription data = getData(elementId);
        if(data instanceof ShopData){
            return (ShopData) data;
        }
        throw new IllegalArgumentException(elementId + " is not instance of ShopData.");
    }

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
                result.putAll(materialService);
                break;
            case MATERIAL:
                    result.putAll(materialService);
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
}
