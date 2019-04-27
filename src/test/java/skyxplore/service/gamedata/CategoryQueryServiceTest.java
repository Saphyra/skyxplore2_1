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

    private Battery battery = new Battery();

    private CoreHull coreHull = new CoreHull();

    private Extender extender = new Extender();

    private Generator generator = new Generator();

    private Material material = new Material();

    private Shield shield = new Shield();

    private Ship ship = new Ship();

    private Storage storage = new Storage();

    private Weapon weapon = new Weapon();

    @InjectMocks
    private CategoryQueryService underTest;

    @Before
    public void init() {
        armor.setId(ARMOR_ID);
        ArmorService armorService = new ArmorService();
        armorService.put(ARMOR_ID, armor);

        battery.setId(BATTERY_ID);
        BatteryService batteryService = new BatteryService();
        batteryService.put(BATTERY_ID, battery);

        coreHull.setId(COREHULL_ID);
        CoreHullService coreHullService = new CoreHullService();
        coreHullService.put(COREHULL_ID, coreHull);

        extender.setId(EXTENDER_ID);
        ExtenderService extenderService = new ExtenderService();
        extenderService.put(EXTENDER_ID, extender);

        generator.setId(GENERATOR_ID);
        GeneratorService generatorService = new GeneratorService();
        generatorService.put(GENERATOR_ID, generator);

        material.setId(MATERIAL_ID);
        MaterialService materialService = new MaterialService();
        materialService.put(MATERIAL_ID, material);

        shield.setId(SHIELD_ID);
        ShieldService shieldService = new ShieldService();
        shieldService.put(SHIELD_ID, shield);

        ship.setId(SHIP_ID);
        ShipService shipService = new ShipService();
        shipService.put(SHIP_ID, ship);

        storage.setId(STORAGE_ID);
        StorageService storageService = new StorageService();
        storageService.put(STORAGE_ID, storage);

        weapon.setId(WEAPON_ID);
        WeaponService weaponService = new WeaponService();
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
    public void testGetElementsOfCategoryShouldPutAll() {
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
    public void testGetElementsOfCategoryShouldPutMaterial() {
        //WHEN
        Map<String, GeneralDescription> result = underTest.getElementsOfCategory(EquipmentCategoryRequest.MATERIAL);
        //THEN
        assertEquals(1, result.size());
        assertEquals(material, result.get(MATERIAL_ID));
    }

    @Test
    public void testGetElementsOfCategoryShouldPutConnector() {
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
    public void testGetElementsOfCategoryShouldPutEnergy() {
        //WHEN
        Map<String, GeneralDescription> result = underTest.getElementsOfCategory(EquipmentCategoryRequest.ENERGY);
        //THEN
        assertEquals(2, result.size());
        assertEquals(battery, result.get(BATTERY_ID));
        assertEquals(generator, result.get(GENERATOR_ID));
    }

    @Test
    public void testGetElementsOfCategoryShouldPutExtender() {
        //WHEN
        Map<String, GeneralDescription> result = underTest.getElementsOfCategory(EquipmentCategoryRequest.EXTENDER);
        //THEN
        assertEquals(1, result.size());
        assertEquals(extender, result.get(EXTENDER_ID));
    }

    @Test
    public void testGetElementsOfCategoryShouldPutCorehull() {
        //WHEN
        Map<String, GeneralDescription> result = underTest.getElementsOfCategory(EquipmentCategoryRequest.COREHULL);
        //THEN
        assertEquals(1, result.size());
        assertEquals(coreHull, result.get(COREHULL_ID));
    }

    @Test
    public void testGetElementsOfCategoryShouldPutStorage() {
        //WHEN
        Map<String, GeneralDescription> result = underTest.getElementsOfCategory(EquipmentCategoryRequest.STORAGE);
        //THEN
        assertEquals(1, result.size());
        assertEquals(storage, result.get(STORAGE_ID));
    }

    @Test
    public void testGetElementsOfCategoryShouldPutDefense() {
        //WHEN
        Map<String, GeneralDescription> result = underTest.getElementsOfCategory(EquipmentCategoryRequest.DEFENSE);
        //THEN
        assertEquals(2, result.size());
        assertEquals(armor, result.get(ARMOR_ID));
        assertEquals(shield, result.get(SHIELD_ID));
    }

    @Test
    public void testGetElementsOfCategoryShouldPutArmor() {
        //WHEN
        Map<String, GeneralDescription> result = underTest.getElementsOfCategory(EquipmentCategoryRequest.ARMOR);
        //THEN
        assertEquals(1, result.size());
        assertEquals(armor, result.get(ARMOR_ID));
    }

    @Test
    public void testGetElementsOfCategoryShouldPutShield() {
        //WHEN
        Map<String, GeneralDescription> result = underTest.getElementsOfCategory(EquipmentCategoryRequest.SHIELD);
        //THEN
        assertEquals(1, result.size());
        assertEquals(shield, result.get(SHIELD_ID));
    }

    @Test
    public void testGetElementsOfCategoryShouldPutShip() {
        //WHEN
        Map<String, GeneralDescription> result = underTest.getElementsOfCategory(EquipmentCategoryRequest.SHIP);
        //THEN
        assertEquals(1, result.size());
        assertEquals(ship, result.get(SHIP_ID));
    }

    @Test
    public void testGetElementsOfCategoryShouldPutWeapon() {
        //WHEN
        Map<String, GeneralDescription> result = underTest.getElementsOfCategory(EquipmentCategoryRequest.WEAPON);
        //THEN
        assertEquals(1, result.size());
        assertEquals(weapon, result.get(WEAPON_ID));
    }
}