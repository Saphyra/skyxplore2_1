package com.github.saphyra.skyxplore.data.gamedata;

import static com.github.saphyra.testing.ExceptionValidator.verifyException;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.github.saphyra.exceptionhandling.exception.NotFoundException;
import com.github.saphyra.skyxplore.common.ErrorCode;
import com.github.saphyra.skyxplore.data.base.AbstractDataService;
import com.github.saphyra.skyxplore.data.gamedata.domain.SlotType;
import com.github.saphyra.skyxplore.data.gamedata.entity.Ability;
import com.github.saphyra.skyxplore.data.gamedata.entity.Armor;
import com.github.saphyra.skyxplore.data.gamedata.entity.EquipmentDescription;
import com.github.saphyra.skyxplore.data.gamedata.entity.FactoryData;
import com.github.saphyra.skyxplore.data.gamedata.entity.GeneralDescription;
import com.github.saphyra.skyxplore.data.gamedata.entity.Material;
import com.github.saphyra.skyxplore.data.gamedata.entity.Weapon;
import com.github.saphyra.skyxplore.data.gamedata.subservice.AbilityService;
import com.github.saphyra.skyxplore.data.gamedata.subservice.ArmorService;
import com.github.saphyra.skyxplore.data.gamedata.subservice.MaterialService;
import com.github.saphyra.skyxplore.data.gamedata.subservice.WeaponService;

@RunWith(MockitoJUnitRunner.class)
public class GameDataQueryServiceTest {
    private static final String DATA_ID = "data_id";
    private static final String ITEM_ID_1 = "item_id_1";
    private static final String ITEM_ID_2 = "item_id_2";
    private static final String ITEM_ID_3 = "item_id_3";
    @Mock
    private AbilityService abilityService;

    @Mock
    private ArmorService armorService;

    @Mock
    private MaterialService materialService;

    @Mock
    private WeaponService weaponService;

    @Mock
    private List<AbstractDataService<? extends GeneralDescription>> generalDescriptionServices;

    @Mock
    private List<AbstractDataService<? extends EquipmentDescription>> equipmentDescriptionServices;

    @InjectMocks
    private GameDataQueryService underTest;

    @Before
    public void setUp(){
        underTest = new GameDataQueryService(generalDescriptionServices, equipmentDescriptionServices);
    }

    @Test
    public void testGetDataShouldThrowExceptionWhenNotFound() {
        //WHEN
        Throwable ex = catchThrowable(() -> underTest.getData(DATA_ID));
        //THEN
        verifyException(ex, NotFoundException.class, ErrorCode.EQUIPMENT_NOT_FOUND);
    }

    @Test
    public void testGetDataShouldReturnWhenFound() {
        //GIVEN
        given(generalDescriptionServices.stream()).willReturn(Stream.of(weaponService));
        Weapon weapon = new Weapon();
        weapon.setId(DATA_ID);
        when(weaponService.containsKey(DATA_ID)).thenReturn(true);
        when(weaponService.get(DATA_ID)).thenReturn(weapon);
        //WHEN
        GeneralDescription result = underTest.getData(DATA_ID);
        //THEN
        assertThat(result).isEqualTo(weapon);
    }

    @Test(expected = IllegalArgumentException.class)
    public void getFactoryData_notFactoryData() {
        //GIVEN
        given(generalDescriptionServices.stream()).willReturn(Stream.of(abilityService));
        Ability ability = new Ability();
        when(abilityService.containsKey(DATA_ID)).thenReturn(true);
        when(abilityService.get(DATA_ID)).thenReturn(ability);
        //WHEN
        underTest.getFactoryData(DATA_ID);
    }

    @Test
    public void getFactoryData() {
        //GIVEN
        given(generalDescriptionServices.stream()).willReturn(Stream.of(weaponService));
        Weapon weapon = new Weapon();
        weapon.setId(DATA_ID);
        when(weaponService.containsKey(DATA_ID)).thenReturn(true);
        when(weaponService.get(DATA_ID)).thenReturn(weapon);
        //WHEN
        FactoryData result = underTest.getFactoryData(DATA_ID);
        //THEN
        assertThat(result).isEqualTo(weapon);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFindBuyableShouldThrowExceptionWhenNotBuyable() {
        //GIVEN
        given(generalDescriptionServices.stream()).willReturn(Stream.of(materialService));
        Material material = new Material();
        material.setId(DATA_ID);
        when(materialService.containsKey(DATA_ID)).thenReturn(true);
        when(materialService.get(DATA_ID)).thenReturn(material);
        //WHEN
        underTest.findBuyable(DATA_ID);
    }

    @Test
    public void testFindBuyableShouldReturnWhenFound() {
        //GIVEN
        given(generalDescriptionServices.stream()).willReturn(Stream.of(weaponService));
        Weapon weapon = new Weapon();
        weapon.setId(DATA_ID);
        when(weaponService.containsKey(DATA_ID)).thenReturn(true);
        when(weaponService.get(DATA_ID)).thenReturn(weapon);
        //WHEN
        FactoryData result = underTest.findBuyable(DATA_ID);
        //THEN
        assertThat(result).isEqualTo(weapon);
    }

    @Test(expected = IllegalArgumentException.class)
    public void findEquipmentDescription_notEquipmentDescription() {
        //GIVEN
        given(generalDescriptionServices.stream()).willReturn(Stream.of(materialService));
        Material material = new Material();
        material.setId(DATA_ID);
        when(materialService.containsKey(DATA_ID)).thenReturn(true);
        when(materialService.get(DATA_ID)).thenReturn(material);
        //WHEN
        underTest.findEquipmentDescription(DATA_ID);
    }

    @Test
    public void findEquipmentDescription() {
        //GIVEN
        given(generalDescriptionServices.stream()).willReturn(Stream.of(armorService));
        Armor armor = new Armor();
        armor.setId(DATA_ID);
        when(armorService.containsKey(DATA_ID)).thenReturn(true);
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
        given(armorService.values()).willReturn(Arrays.asList(armor1, armor2, armor3));
        //WHEN
        List<EquipmentDescription> result = underTest.getEquipmentDescriptionBySlotAndLevel(SlotType.WEAPON, 1);
        //THEN
        assertThat(result).containsExactly(armor1);
    }
}