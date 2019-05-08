package com.github.saphyra.skyxplore.gamedata;

import com.github.saphyra.skyxplore.common.exception.EquipmentNotFoundException;
import com.github.saphyra.skyxplore.gamedata.entity.Ability;
import com.github.saphyra.skyxplore.gamedata.entity.FactoryData;
import com.github.saphyra.skyxplore.gamedata.entity.GeneralDescription;
import com.github.saphyra.skyxplore.gamedata.entity.Material;
import com.github.saphyra.skyxplore.gamedata.entity.Weapon;
import com.github.saphyra.skyxplore.gamedata.subservice.AbilityService;
import com.github.saphyra.skyxplore.gamedata.subservice.ArmorService;
import com.github.saphyra.skyxplore.gamedata.subservice.BatteryService;
import com.github.saphyra.skyxplore.gamedata.subservice.CoreHullService;
import com.github.saphyra.skyxplore.gamedata.subservice.ExtenderService;
import com.github.saphyra.skyxplore.gamedata.subservice.GeneratorService;
import com.github.saphyra.skyxplore.gamedata.subservice.MaterialService;
import com.github.saphyra.skyxplore.gamedata.subservice.ShieldService;
import com.github.saphyra.skyxplore.gamedata.subservice.ShipService;
import com.github.saphyra.skyxplore.gamedata.subservice.StorageService;
import com.github.saphyra.skyxplore.gamedata.subservice.WeaponService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

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
        assertThat(result).isEqualTo(weapon);
    }

    @Test(expected = IllegalArgumentException.class)
    public void getFactoryData_notFactoryData() {
        //GIVEN
        Ability ability = new Ability();
        when(abilityService.get(DATA_ID)).thenReturn(ability);
        //WHEN
        underTest.getFactoryData(DATA_ID);
    }

    @Test
    public void getFactoryData() {
        //GIVEN
        Weapon weapon = new Weapon();
        weapon.setId(DATA_ID);
        when(weaponService.get(DATA_ID)).thenReturn(weapon);
        //WHEN
        FactoryData result = underTest.getFactoryData(DATA_ID);
        //THEN
        assertThat(result).isEqualTo(weapon);
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
        assertThat(result).isEqualTo(weapon);
    }
}