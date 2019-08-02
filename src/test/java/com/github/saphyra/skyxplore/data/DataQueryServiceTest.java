package com.github.saphyra.skyxplore.data;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.github.saphyra.skyxplore.common.exception.EquipmentNotFoundException;
import com.github.saphyra.skyxplore.data.base.AbstractGameDataService;
import com.github.saphyra.skyxplore.data.domain.SlotType;
import com.github.saphyra.skyxplore.data.entity.Ability;
import com.github.saphyra.skyxplore.data.entity.Armor;
import com.github.saphyra.skyxplore.data.entity.EquipmentDescription;
import com.github.saphyra.skyxplore.data.entity.FactoryData;
import com.github.saphyra.skyxplore.data.entity.GeneralDescription;
import com.github.saphyra.skyxplore.data.entity.Material;
import com.github.saphyra.skyxplore.data.entity.Weapon;
import com.github.saphyra.skyxplore.data.subservice.AbilityService;
import com.github.saphyra.skyxplore.data.subservice.ArmorService;
import com.github.saphyra.skyxplore.data.subservice.BatteryService;
import com.github.saphyra.skyxplore.data.subservice.CoreHullService;
import com.github.saphyra.skyxplore.data.subservice.ExtenderService;
import com.github.saphyra.skyxplore.data.subservice.GeneratorService;
import com.github.saphyra.skyxplore.data.subservice.MaterialService;
import com.github.saphyra.skyxplore.data.subservice.ShieldService;
import com.github.saphyra.skyxplore.data.subservice.ShipService;
import com.github.saphyra.skyxplore.data.subservice.StorageService;
import com.github.saphyra.skyxplore.data.subservice.WeaponService;

@RunWith(MockitoJUnitRunner.class)
public class DataQueryServiceTest {
    private static final String DATA_ID = "data_id";
    private static final String ITEM_ID_1 = "item_id_1";
    private static final String ITEM_ID_2 = "item_id_2";
    private static final String ITEM_ID_3 = "item_id_3";
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

    @Mock
    private List<AbstractGameDataService<? extends EquipmentDescription>> equipmentDescriptionServices;

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

    @Test(expected = IllegalArgumentException.class)
    public void findEquipmentDescription_notEquipmentDescription() {
        //GIVEN
        Material material = new Material();
        material.setId(DATA_ID);
        when(materialService.get(DATA_ID)).thenReturn(material);
        //WHEN
        underTest.findEquipmentDescription(DATA_ID);
    }

    @Test
    public void findEquipmentDescription() {
        //GIVEN
        Armor armor = new Armor();
        armor.setId(DATA_ID);
        when(armorService.get(DATA_ID)).thenReturn(armor);
        //WHEN
        EquipmentDescription result = underTest.findEquipmentDescription(DATA_ID);
        //THEN
        assertThat(result).isEqualTo(armor);
    }

    @Test
    public void getEquipmentDescriptionBySlotAndLevel() {
        //GIVEN
        given(equipmentDescriptionServices.stream()).willReturn(Stream.of(armorService));

        Armor armor1 = new Armor();
        armor1.setId(ITEM_ID_1);
        armor1.setSlot(SlotType.WEAPON.name());
        armor1.setLevel(1);

        Armor armor2 = new Armor();
        armor2.setId(ITEM_ID_2);
        armor2.setSlot(SlotType.WEAPON.name());
        armor2.setLevel(2);

        Armor armor3 = new Armor();
        armor3.setId(ITEM_ID_3);
        armor3.setSlot(SlotType.DEFENSE.name());
        armor3.setLevel(1);
        given(armorService.values()).willReturn(Arrays.asList(armor1, armor2, armor3));
        //WHEN
        List<EquipmentDescription> result = underTest.getEquipmentDescriptionBySlotAndLevel(SlotType.WEAPON, 1);
        //THEN
        assertThat(result).containsExactly(armor1);
    }
}