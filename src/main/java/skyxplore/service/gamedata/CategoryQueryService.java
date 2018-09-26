package skyxplore.service.gamedata;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import skyxplore.controller.request.character.EquipmentCategoryRequest;
import skyxplore.dataaccess.gamedata.entity.abstractentity.GeneralDescription;
import skyxplore.dataaccess.gamedata.subservice.ArmorService;
import skyxplore.dataaccess.gamedata.subservice.BatteryService;
import skyxplore.dataaccess.gamedata.subservice.CoreHullService;
import skyxplore.dataaccess.gamedata.subservice.ExtenderService;
import skyxplore.dataaccess.gamedata.subservice.GeneratorService;
import skyxplore.dataaccess.gamedata.subservice.MaterialService;
import skyxplore.dataaccess.gamedata.subservice.ShieldService;
import skyxplore.dataaccess.gamedata.subservice.ShipService;
import skyxplore.dataaccess.gamedata.subservice.StorageService;
import skyxplore.dataaccess.gamedata.subservice.WeaponService;

@Service
@RequiredArgsConstructor
@Slf4j
//TODO unit test
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

    public Map<String, GeneralDescription> getElementsOfCategory(EquipmentCategoryRequest request) {
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
