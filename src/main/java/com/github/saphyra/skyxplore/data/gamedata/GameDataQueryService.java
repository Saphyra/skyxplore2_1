package com.github.saphyra.skyxplore.data.gamedata;

import com.github.saphyra.skyxplore.common.ExceptionFactory;
import com.github.saphyra.skyxplore.data.base.AbstractDataService;
import com.github.saphyra.skyxplore.data.gamedata.domain.SlotType;
import com.github.saphyra.skyxplore.data.gamedata.entity.EquipmentDescription;
import com.github.saphyra.skyxplore.data.gamedata.entity.FactoryData;
import com.github.saphyra.skyxplore.data.gamedata.entity.GeneralDescription;
import com.github.saphyra.skyxplore.data.gamedata.entity.ShopData;
import com.github.saphyra.skyxplore.data.gamedata.subservice.AbilityService;
import com.github.saphyra.skyxplore.data.gamedata.subservice.ArmorService;
import com.github.saphyra.skyxplore.data.gamedata.subservice.BatteryService;
import com.github.saphyra.skyxplore.data.gamedata.subservice.CoreHullService;
import com.github.saphyra.skyxplore.data.gamedata.subservice.ExtenderService;
import com.github.saphyra.skyxplore.data.gamedata.subservice.GeneratorService;
import com.github.saphyra.skyxplore.data.gamedata.subservice.MaterialService;
import com.github.saphyra.skyxplore.data.gamedata.subservice.ShieldService;
import com.github.saphyra.skyxplore.data.gamedata.subservice.ShipService;
import com.github.saphyra.skyxplore.data.gamedata.subservice.StorageService;
import com.github.saphyra.skyxplore.data.gamedata.subservice.WeaponService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
class GameDataQueryService {
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
    private final List<AbstractDataService<? extends EquipmentDescription>> equipmentDescriptionServices;

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
            throw ExceptionFactory.equipmentNotFound(id);
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

    EquipmentDescription findEquipmentDescription(String itemId) {
        GeneralDescription data = getData(itemId);
        if (data instanceof EquipmentDescription) {
            return (EquipmentDescription) data;
        }
        throw new IllegalArgumentException(itemId + " is not instance of EquipmentDescription.");
    }

    List<EquipmentDescription> getEquipmentDescriptionBySlotAndLevel(SlotType slotType, int level) {
        return equipmentDescriptionServices.stream()
            .flatMap(service -> service.values().stream())
            .filter(equipmentDescription -> equipmentDescription.getSlot().equalsIgnoreCase(slotType.name()))
            .filter(equipmentDescription -> equipmentDescription.getLevel() == level)
            .collect(Collectors.toList());
    }
}
