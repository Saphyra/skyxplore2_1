package skyxplore.service.gamedata;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import skyxplore.dataaccess.gamedata.entity.abstractentity.FactoryData;
import skyxplore.dataaccess.gamedata.entity.abstractentity.GeneralDescription;
import skyxplore.dataaccess.gamedata.entity.abstractentity.ShopData;
import skyxplore.dataaccess.gamedata.subservice.AbilityService;
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
import skyxplore.exception.EquipmentNotFoundException;

@SuppressWarnings("WeakerAccess")
@Service
@RequiredArgsConstructor
@Slf4j
//TODO unit test
public class DataQueryService {
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
}
