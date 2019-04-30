package org.github.saphyra.skyxplore.gamedata;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.github.saphyra.skyxplore.gamedata.entity.abstractentity.FactoryData;
import org.github.saphyra.skyxplore.gamedata.entity.abstractentity.GeneralDescription;
import org.github.saphyra.skyxplore.gamedata.entity.abstractentity.ShopData;
import org.github.saphyra.skyxplore.gamedata.subservice.AbilityService;
import org.github.saphyra.skyxplore.gamedata.subservice.ArmorService;
import org.github.saphyra.skyxplore.gamedata.subservice.BatteryService;
import org.github.saphyra.skyxplore.gamedata.subservice.CoreHullService;
import org.github.saphyra.skyxplore.gamedata.subservice.ExtenderService;
import org.github.saphyra.skyxplore.gamedata.subservice.GeneratorService;
import org.github.saphyra.skyxplore.gamedata.subservice.MaterialService;
import org.github.saphyra.skyxplore.gamedata.subservice.ShieldService;
import org.github.saphyra.skyxplore.gamedata.subservice.ShipService;
import org.github.saphyra.skyxplore.gamedata.subservice.StorageService;
import org.github.saphyra.skyxplore.gamedata.subservice.WeaponService;
import org.github.saphyra.skyxplore.common.exception.EquipmentNotFoundException;

@Service
@RequiredArgsConstructor
@Slf4j
class DataQueryService {
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

    GeneralDescription getData(String id) {
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

    FactoryData getFactoryData(String elementId) {
        GeneralDescription element = getData(elementId);
        if (element instanceof FactoryData) {
            return (FactoryData) element;
        }
        throw new IllegalArgumentException(elementId + " is not instance of FactoryData");
    }

    ShopData findBuyable(String elementId) {
        GeneralDescription data = getData(elementId);
        if (data instanceof ShopData) {
            return (ShopData) data;
        }
        throw new IllegalArgumentException(elementId + " is not instance of ShopData.");
    }
}
