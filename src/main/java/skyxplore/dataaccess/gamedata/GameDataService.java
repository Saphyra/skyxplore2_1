package skyxplore.dataaccess.gamedata;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import skyxplore.dataaccess.gamedata.subservice.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class GameDataService {
    private final AbilityService abilityService;
    private  final ArmorService armorService;
    private  final BatteryService batteryService;
    private final CoreHullService coreHullService;
    private final ExtenderService extenderService;
    private final GeneratorService generatorService;
    private final MaterialService materialService;
    private final ShieldService shieldService;
    private final StorageService storageService;
    private final  WeaponService weaponService;
}
