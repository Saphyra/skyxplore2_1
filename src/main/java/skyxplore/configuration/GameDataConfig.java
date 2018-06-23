package skyxplore.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import skyxplore.dataaccess.gamedata.subservice.*;

@SuppressWarnings("unused")
@Configuration
public class GameDataConfig {
    @Bean
    public AbilityService abilityService(){
        AbilityService abilityService = new AbilityService("ability");
        abilityService.init();
        return abilityService;
    }

    @Bean
    public MaterialService materialService(){
        MaterialService materialService = new MaterialService("material");
        materialService.init();
        return materialService;
    }

    @Bean
    public BatteryService batteryService(){
        BatteryService batteryService = new BatteryService("connector/battery");
        batteryService.init();
        return batteryService;
    }

    @Bean
    public CoreHullService coreHullService(){
        CoreHullService coreHullService = new CoreHullService("connector/corehull");
        coreHullService.init();
        return coreHullService;
    }

    @Bean
    public ExtenderService extenderService(){
        ExtenderService extenderService = new ExtenderService("connector/extender");
        extenderService.init();
        return extenderService;
    }

    @Bean
    public GeneratorService generatorService(){
        GeneratorService generatorService = new GeneratorService("connector/generator");
        generatorService.init();
        return generatorService;
    }

    @Bean
    public StorageService storageService(){
        StorageService storageService = new StorageService("connector/storage");
        storageService.init();
        return storageService;
    }

    @Bean
    public ArmorService armorService(){
        ArmorService armorService = new ArmorService("defense/armor");
        armorService.init();
        return armorService;
    }

    @Bean
    public ShieldService shieldService(){
        ShieldService shieldService = new ShieldService("defense/shield");
        shieldService.init();
        return shieldService;
    }

    @Bean
    public ShipService shipService(){
        ShipService shipService = new ShipService("ship");
        shipService.init();
        return shipService;
    }

    @Bean
    public WeaponService weaponService(){
        WeaponService weaponService = new WeaponService("weapon");
        weaponService.init();
        return weaponService;
    }
}
