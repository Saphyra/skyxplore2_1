package skyxplore.service.gamedata;

import java.util.HashMap;
import java.util.Map;

import org.github.saphyra.skyxplore.gamedata.entity.abstractentity.GeneralDescription;
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
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import skyxplore.controller.request.character.EquipmentCategoryRequest;

@Service
@RequiredArgsConstructor
@Slf4j
public class CategoryQueryService {
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

    Map<String, GeneralDescription> getElementsOfCategory(EquipmentCategoryRequest request) {
        log.info("Querying equipments of friendrequest {}", request);
        Map<String, GeneralDescription> result = new HashMap<>();
        switch (request) {
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
}
