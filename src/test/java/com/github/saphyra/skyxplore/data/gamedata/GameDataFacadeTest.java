package com.github.saphyra.skyxplore.data.gamedata;

import com.github.saphyra.skyxplore.data.gamedata.domain.SlotType;
import com.github.saphyra.skyxplore.data.gamedata.entity.Armor;
import com.github.saphyra.skyxplore.data.gamedata.entity.EquipmentDescription;
import com.github.saphyra.skyxplore.data.gamedata.entity.FactoryData;
import com.github.saphyra.skyxplore.data.gamedata.entity.GeneralDescription;
import com.github.saphyra.testing.TestGeneralDescription;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class GameDataFacadeTest {
    private static final String DATA_ID = "data_id";
    private static final String DATA_CATEGORY = "data_category";
    private static final String DATA_SLOT = "data_slot";

    @Mock
    private GameDataQueryService dataQueryService;

    @InjectMocks
    private GameDataFacade underTest;

    @Mock
    private EquipmentDescription equipmentDescription;

    @Test
    public void testGetDataShouldCallServiceAndReturn() {
        //GIVEN
        GeneralDescription generalDescription = new TestGeneralDescription(DATA_ID, DATA_CATEGORY, DATA_SLOT);
        when(dataQueryService.getData(DATA_ID)).thenReturn(generalDescription);
        //WHEN
        GeneralDescription result = underTest.getData(DATA_ID);
        //THEN
        verify(dataQueryService).getData(DATA_ID);
        assertThat(result).isEqualTo(generalDescription);
    }

    @Test
    public void testGetFactoryDataShouldCallServiceAndReturn() {
        //GIVEN
        Armor armor = new Armor();
        armor.setId(DATA_ID);
        when(dataQueryService.getFactoryData(DATA_ID)).thenReturn(armor);
        //WHEN
        FactoryData result = underTest.getFactoryData(DATA_ID);
        //THEN
        verify(dataQueryService).getFactoryData(DATA_ID);
        assertThat(result).isEqualTo(armor);
    }

    @Test
    public void testFindBuyableShouldCallServiceAndReturn() {
        //GIVEN
        Armor armor = new Armor();
        armor.setId(DATA_ID);
        when(dataQueryService.findBuyable(DATA_ID)).thenReturn(armor);
        //WHEN
        FactoryData result = underTest.findBuyable(DATA_ID);
        //THEN
        verify(dataQueryService).findBuyable(DATA_ID);
        assertThat(result).isEqualTo(armor);
    }

    @Test
    public void findEquipmentDescription() {
        //GIVEN
        given(dataQueryService.findEquipmentDescription(DATA_ID)).willReturn(equipmentDescription);
        //WHEN
        EquipmentDescription result = underTest.findEquipmentDescription(DATA_ID);
        //THEN
        assertThat(result).isEqualTo(equipmentDescription);
    }

    @Test
    public void isUpgradeable_true() {
        //GIVEN
        given(dataQueryService.findEquipmentDescription(DATA_ID)).willReturn(equipmentDescription);
        given(equipmentDescription.getLevel()).willReturn(2);
        //WHEN
        boolean result = underTest.isUpgradable(DATA_ID);
        //THEN
        assertThat(result).isTrue();
    }

    @Test
    public void isUpgradeable_false() {
        //GIVEN
        given(dataQueryService.findEquipmentDescription(DATA_ID)).willReturn(equipmentDescription);
        given(equipmentDescription.getLevel()).willReturn(3);
        //WHEN
        boolean result = underTest.isUpgradable(DATA_ID);
        //THEN
        assertThat(result).isFalse();
    }

    @Test
    public void getEquipmentDescriptionBySlotAndLevel() {
        //GIVEN
        given(dataQueryService.getEquipmentDescriptionBySlotAndLevel(SlotType.WEAPON, 1)).willReturn(Arrays.asList(equipmentDescription));
        //WHEN
        List<EquipmentDescription> result = underTest.getEquipmentDescriptionBySlotAndLevel(SlotType.WEAPON, 1);
        //THEN
        assertThat(result).containsExactly(equipmentDescription);
    }
}