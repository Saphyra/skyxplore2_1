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
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

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

    public Set<GeneralDescription> collectEquipmentData(EquippedShip ship) {
        Set<GeneralDescription> result = new HashSet<>();
        result.add(getShip(ship.getShipType()));
        result.addAll(getData(ship.getConnectorEquipped()));
        result.addAll(getData(ship.getDefenseSlot()));
        result.addAll(getData(ship.getWeaponSlot()));
        return result;
    }

    private Set<GeneralDescription> getData(EquippedSlot slot) {
        Set<GeneralDescription> result = new HashSet<>();
        result.addAll(getData(slot.getFrontEquipped()));
        result.addAll(getData(slot.getLeftEquipped()));
        result.addAll(getData(slot.getRightEquipped()));
        result.addAll(getData(slot.getBackEquipped()));
        return result;
    }

    private Set<GeneralDescription> getData(Collection<String> ids) {
        return ids.stream().map(this::getData).collect(Collectors.toSet());
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
