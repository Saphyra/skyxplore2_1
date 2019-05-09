package com.github.saphyra.skyxplore.gamedata;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.github.saphyra.skyxplore.gamedata.entity.Armor;
import com.github.saphyra.skyxplore.gamedata.entity.FactoryData;
import com.github.saphyra.skyxplore.gamedata.entity.GeneralDescription;
import com.github.saphyra.skyxplore.testing.TestGeneralDescription;

@RunWith(MockitoJUnitRunner.class)
public class GameDataFacadeTest {
    private static final String DATA_ID = "data_id";
    private static final String DATA_CATEGORY = "data_category";
    private static final String DATA_SLOT = "data_slot";
    @Mock
    private DataQueryService dataQueryService;

    @InjectMocks
    private GameDataFacade underTest;

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
}