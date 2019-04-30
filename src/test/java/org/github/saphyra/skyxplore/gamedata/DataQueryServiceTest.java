package org.github.saphyra.skyxplore.gamedata;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import skyxplore.dataaccess.gamedata.entity.Material;
import skyxplore.dataaccess.gamedata.entity.Weapon;
import skyxplore.dataaccess.gamedata.entity.abstractentity.FactoryData;
import skyxplore.dataaccess.gamedata.entity.abstractentity.GeneralDescription;
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

@RunWith(MockitoJUnitRunner.class)
public class DataQueryServiceTest {
    private static final String DATA_ID = "data_id";
    @Mock
    @SuppressWarnings("unused")
    private AbilityService abilityService;

    @Mock
    @SuppressWarnings("unused")
    private ArmorService armorService;

    @Mock
    @SuppressWarnings("unused")
    private BatteryService batteryService;

    @Mock
    @SuppressWarnings("unused")
    private CoreHullService coreHullService;

    @Mock
    @SuppressWarnings("unused")
    private ExtenderService extenderService;

    @Mock
    @SuppressWarnings("unused")
    private GeneratorService generatorService;

    @Mock
    private MaterialService materialService;

    @Mock
    @SuppressWarnings("unused")
    private ShieldService shieldService;

    @Mock
    @SuppressWarnings("unused")
    private ShipService shipService;

    @Mock
    @SuppressWarnings("unused")
    private StorageService storageService;

    @Mock
    private WeaponService weaponService;

    @InjectMocks
    private DataQueryService underTest;

    @Test(expected = EquipmentNotFoundException.class)
    public void testGetDataShouldThrowExceptionWhenNotFound() {
        underTest.getData(DATA_ID);
    }

    @Test
    public void testGetDataShouldReturnWhenFound() {
        //GIVEN
        Weapon weapon = new Weapon();
        weapon.setId(DATA_ID);
        when(weaponService.get(DATA_ID)).thenReturn(weapon);
        //WHEN
        GeneralDescription result = underTest.getData(DATA_ID);
        //THEN
        assertEquals(weapon, result);
    }

    @Test
    public void testGetFactoryDataShouldThrowExceptionWhenNotFactoryData() {
        //GIVEN
        Weapon weapon = new Weapon();
        weapon.setId(DATA_ID);
        when(weaponService.get(DATA_ID)).thenReturn(weapon);
        //WHEN
        FactoryData result = underTest.getFactoryData(DATA_ID);
        //THEN
        assertEquals(weapon, result);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFindBuyableShouldThrowExceptionWhenNotBuyable() {
        //GIVEN
        Material material = new Material();
        material.setId(DATA_ID);
        when(materialService.get(DATA_ID)).thenReturn(material);
        //WHEN
        underTest.findBuyable(DATA_ID);
    }

    @Test
    public void testFindBuyableShouldReturnWhenFound() {
        //GIVEN
        Weapon weapon = new Weapon();
        weapon.setId(DATA_ID);
        when(weaponService.get(DATA_ID)).thenReturn(weapon);
        //WHEN
        FactoryData result = underTest.findBuyable(DATA_ID);
        //THEN
        assertEquals(weapon, result);
    }
}