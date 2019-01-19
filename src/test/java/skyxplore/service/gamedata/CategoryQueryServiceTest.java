package skyxplore.service.gamedata;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;
import skyxplore.controller.request.character.EquipmentCategoryRequest;
import skyxplore.dataaccess.gamedata.entity.*;
import skyxplore.dataaccess.gamedata.entity.abstractentity.GeneralDescription;
import skyxplore.dataaccess.gamedata.subservice.*;

import java.util.Map;

import static org.junit.Assert.assertEquals;
import static skyxplore.testutil.TestUtils.MATERIAL_ID;

@RunWith(MockitoJUnitRunner.class)
public class CategoryQueryServiceTest {
    private static final String SOURCE = "source";

    private static final String ARMOR_ID = "armor_id";
    private static final String BATTERY_ID = "battery_id";
    private static final String COREHULL_ID = "corehull_id";
    private static final String EXTENDER_ID = "extender_id";
    private static final String GENERATOR_ID = "generator_id";
    private static final String SHIELD_ID = "shield_id";
    private static final String SHIP_ID = "ship_id";
    private static final String STORAGE_ID = "storage_id";
    private static final String WEAPON_ID = "weapon_id";

    private Armor armor = new Armor();
    private ArmorService armorService;

    private Battery battery = new Battery();
    private BatteryService batteryService;

    private CoreHull coreHull = new CoreHull();
    private CoreHullService coreHullService;

    private Extender extender = new Extender();
    private ExtenderService extenderService;

    private Generator generator = new Generator();
    private GeneratorService generatorService;

    private Material material = new Material();
    private MaterialService materialService;

    private Shield shield = new Shield();
    private ShieldService shieldService;

    private Ship ship = new Ship();
    private ShipService shipService;

    private Storage storage = new Storage();
    private StorageService storageService;

    private Weapon weapon = new Weapon();
    private WeaponService weaponService;

    @InjectMocks
    private CategoryQueryService underTest;

    @Before
    public void init() {
        armor.setId(ARMOR_ID);
        armorService = new ArmorService(SOURCE);
        armorService.put(ARMOR_ID, armor);

        battery.setId(BATTERY_ID);
        batteryService = new BatteryService(SOURCE);
        batteryService.put(BATTERY_ID, battery);

        coreHull.setId(COREHULL_ID);
        coreHullService = new CoreHullService(SOURCE);
        coreHullService.put(COREHULL_ID, coreHull);

        extender.setId(EXTENDER_ID);
        extenderService = new ExtenderService(SOURCE);
        extenderService.put(EXTENDER_ID, extender);

        generator.setId(GENERATOR_ID);
        generatorService = new GeneratorService(SOURCE);
        generatorService.put(GENERATOR_ID, generator);

        material.setId(MATERIAL_ID);
        materialService = new MaterialService(SOURCE);
        materialService.put(MATERIAL_ID, material);

        shield.setId(SHIELD_ID);
        shieldService = new ShieldService(SOURCE);
        shieldService.put(SHIELD_ID, shield);

        ship.setId(SHIP_ID);
        shipService = new ShipService(SOURCE);
        shipService.put(SHIP_ID, ship);

        storage.setId(STORAGE_ID);
        storageService = new StorageService(SOURCE);
        storageService.put(STORAGE_ID, storage);

        weapon.setId(WEAPON_ID);
        weaponService = new WeaponService(SOURCE);
        weaponService.put(WEAPON_ID, weapon);

        underTest = new CategoryQueryService(
            armorService,
            batteryService,
            coreHullService,
            extenderService,
            generatorService,
            materialService,
            shieldService,
            shipService,
            storageService,
            weaponService
        );
    }

    @Test
    public void testGetElementsOfCategoryShouldPutAll(){
        //WHEN
        Map<String, GeneralDescription> result = underTest.getElementsOfCategory(EquipmentCategoryRequest.ALL);
        //THEN
        assertEquals(10, result.size());
        assertEquals(armor, result.get(ARMOR_ID));
        assertEquals(battery, result.get(BATTERY_ID));
        assertEquals(coreHull, result.get(COREHULL_ID));
        assertEquals(extender, result.get(EXTENDER_ID));
        assertEquals(generator, result.get(GENERATOR_ID));
        assertEquals(material, result.get(MATERIAL_ID));
        assertEquals(shield, result.get(SHIELD_ID));
        assertEquals(ship, result.get(SHIP_ID));
        assertEquals(storage, result.get(STORAGE_ID));
        assertEquals(weapon, result.get(WEAPON_ID));
    }

    @Test
    public void testGetElementsOfCategoryShouldPutMaterial(){
        //WHEN
        Map<String, GeneralDescription> result = underTest.getElementsOfCategory(EquipmentCategoryRequest.MATERIAL);
        //THEN
        assertEquals(1, result.size());
        assertEquals(material, result.get(MATERIAL_ID));
    }

    @Test
    public void testGetElementsOfCategoryShouldPutConnector(){
        //WHEN
        Map<String, GeneralDescription> result = underTest.getElementsOfCategory(EquipmentCategoryRequest.CONNECTOR);
        //THEN
        assertEquals(5, result.size());
        assertEquals(battery, result.get(BATTERY_ID));
        assertEquals(coreHull, result.get(COREHULL_ID));
        assertEquals(extender, result.get(EXTENDER_ID));
        assertEquals(generator, result.get(GENERATOR_ID));
        assertEquals(storage, result.get(STORAGE_ID));
    }

    @Test
    public void testGetElementsOfCategoryShouldPutEnergy(){
        //WHEN
        Map<String, GeneralDescription> result = underTest.getElementsOfCategory(EquipmentCategoryRequest.ENERGY);
        //THEN
        assertEquals(2, result.size());
        assertEquals(battery, result.get(BATTERY_ID));
        assertEquals(generator, result.get(GENERATOR_ID));
    }

    @Test
    public void testGetElementsOfCategoryShouldPutExtender(){
        //WHEN
        Map<String, GeneralDescription> result = underTest.getElementsOfCategory(EquipmentCategoryRequest.EXTENDER);
        //THEN
        assertEquals(1, result.size());
        assertEquals(extender, result.get(EXTENDER_ID));
    }

    @Test
    public void testGetElementsOfCategoryShouldPutCorehull(){
        //WHEN
        Map<String, GeneralDescription> result = underTest.getElementsOfCategory(EquipmentCategoryRequest.COREHULL);
        //THEN
        assertEquals(1, result.size());
        assertEquals(coreHull, result.get(COREHULL_ID));
    }

    @Test
    public void testGetElementsOfCategoryShouldPutStorage(){
        //WHEN
        Map<String, GeneralDescription> result = underTest.getElementsOfCategory(EquipmentCategoryRequest.STORAGE);
        //THEN
        assertEquals(1, result.size());
        assertEquals(storage, result.get(STORAGE_ID));
    }

    @Test
    public void testGetElementsOfCategoryShouldPutDefense(){
        //WHEN
        Map<String, GeneralDescription> result = underTest.getElementsOfCategory(EquipmentCategoryRequest.DEFENSE);
        //THEN
        assertEquals(2, result.size());
        assertEquals(armor, result.get(ARMOR_ID));
        assertEquals(shield, result.get(SHIELD_ID));
    }

    @Test
    public void testGetElementsOfCategoryShouldPutArmor(){
        //WHEN
        Map<String, GeneralDescription> result = underTest.getElementsOfCategory(EquipmentCategoryRequest.ARMOR);
        //THEN
        assertEquals(1, result.size());
        assertEquals(armor, result.get(ARMOR_ID));
    }

    @Test
    public void testGetElementsOfCategoryShouldPutShield(){
        //WHEN
        Map<String, GeneralDescription> result = underTest.getElementsOfCategory(EquipmentCategoryRequest.SHIELD);
        //THEN
        assertEquals(1, result.size());
        assertEquals(shield, result.get(SHIELD_ID));
    }

    @Test
    public void testGetElementsOfCategoryShouldPutShip(){
        //WHEN
        Map<String, GeneralDescription> result = underTest.getElementsOfCategory(EquipmentCategoryRequest.SHIP);
        //THEN
        assertEquals(1, result.size());
        assertEquals(ship, result.get(SHIP_ID));
    }

    @Test
    public void testGetElementsOfCategoryShouldPutWeapon(){
        //WHEN
        Map<String, GeneralDescription> result = underTest.getElementsOfCategory(EquipmentCategoryRequest.WEAPON);
        //THEN
        assertEquals(1, result.size());
        assertEquals(weapon, result.get(WEAPON_ID));
    }
}