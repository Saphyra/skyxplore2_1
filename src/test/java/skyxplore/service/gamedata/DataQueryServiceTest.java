package skyxplore.service.gamedata;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import skyxplore.dataaccess.gamedata.entity.Material;
import skyxplore.dataaccess.gamedata.entity.Weapon;
import skyxplore.dataaccess.gamedata.entity.abstractentity.FactoryData;
import skyxplore.dataaccess.gamedata.entity.abstractentity.GeneralDescription;
import skyxplore.dataaccess.gamedata.subservice.*;
import skyxplore.exception.EquipmentNotFoundException;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
import static skyxplore.testutil.TestUtils.DATA_ELEMENT;

@RunWith(MockitoJUnitRunner.class)
public class DataQueryServiceTest {
    @Mock
    private AbilityService abilityService;

    @Mock
    private ArmorService armorService;

    @Mock
    private BatteryService batteryService;

    @Mock
    private CoreHullService coreHullService;

    @Mock
    private ExtenderService extenderService;

    @Mock
    private GeneratorService generatorService;

    @Mock
    private MaterialService materialService;

    @Mock
    private ShieldService shieldService;

    @Mock
    private ShipService shipService;

    @Mock
    private StorageService storageService;

    @Mock
    private WeaponService weaponService;

    @InjectMocks
    private DataQueryService underTest;

    @Test(expected = EquipmentNotFoundException.class)
    public void testGetDataShouldThrowExceptionWhenNotFound() {
        underTest.getData(DATA_ELEMENT);
    }

    @Test
    public void testGetDataShouldReturnWhenFound() {
        //GIVEN
        Weapon weapon = new Weapon();
        weapon.setId(DATA_ELEMENT);
        when(weaponService.get(DATA_ELEMENT)).thenReturn(weapon);
        //WHEN
        GeneralDescription result = underTest.getData(DATA_ELEMENT);
        //THEN
        assertEquals(weapon, result);
    }

    @Test
    public void testGetFactoryDataShouldThrowExceptionWhenNotFactoryData() {
        //GIVEN
        Weapon weapon = new Weapon();
        weapon.setId(DATA_ELEMENT);
        when(weaponService.get(DATA_ELEMENT)).thenReturn(weapon);
        //WHEN
        FactoryData result = underTest.getFactoryData(DATA_ELEMENT);
        //THEN
        assertEquals(weapon, result);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFindBuyableShouldThrowExceptionWhenNotBuyable(){
        //GIVEN
        Material material = new Material();
        material.setId(DATA_ELEMENT);
        when(materialService.get(DATA_ELEMENT)).thenReturn(material);
        //WHEN
        underTest.findBuyable(DATA_ELEMENT);
    }

    @Test
    public void testFindBuyableShouldReturnWhenFound(){
        //GIVEN
        Weapon weapon = new Weapon();
        weapon.setId(DATA_ELEMENT);
        when(weaponService.get(DATA_ELEMENT)).thenReturn(weapon);
        //WHEN
        FactoryData result = underTest.findBuyable(DATA_ELEMENT);
        //THEN
        assertEquals(weapon, result);
    }
}