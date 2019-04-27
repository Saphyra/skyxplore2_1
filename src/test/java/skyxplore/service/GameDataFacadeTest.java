package skyxplore.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import skyxplore.dataaccess.gamedata.entity.Armor;
import skyxplore.dataaccess.gamedata.entity.abstractentity.FactoryData;
import skyxplore.dataaccess.gamedata.entity.abstractentity.GeneralDescription;
import skyxplore.service.gamedata.DataQueryService;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static skyxplore.testutil.TestUtils.DATA_ELEMENT;
import static skyxplore.testutil.TestUtils.createGeneralDescription;

@RunWith(MockitoJUnitRunner.class)
public class GameDataFacadeTest {
    @Mock
    private DataQueryService dataQueryService;

    @InjectMocks
    private GameDataFacade underTest;

    @Test
    public void testGetDataShouldCallServiceAndReturn() {
        //GIVEN
        GeneralDescription generalDescription = createGeneralDescription();
        when(dataQueryService.getData(DATA_ELEMENT)).thenReturn(generalDescription);
        //WHEN
        GeneralDescription result = underTest.getData(DATA_ELEMENT);
        //THEN
        verify(dataQueryService).getData(DATA_ELEMENT);
        assertEquals(generalDescription, result);
    }

    @Test
    public void testGetFactoryDataShouldCallServiceAndReturn() {
        //GIVEN
        Armor armor = new Armor();
        armor.setId(DATA_ELEMENT);
        when(dataQueryService.getFactoryData(DATA_ELEMENT)).thenReturn(armor);
        //WHEN
        FactoryData result = underTest.getFactoryData(DATA_ELEMENT);
        //THEN
        verify(dataQueryService).getFactoryData(DATA_ELEMENT);
        assertEquals(armor, result);
    }

    @Test
    public void testFindBuyableShouldCallServiceAndReturn() {
        //GIVEN
        Armor armor = new Armor();
        armor.setId(DATA_ELEMENT);
        when(dataQueryService.findBuyable(DATA_ELEMENT)).thenReturn(armor);
        //WHEN
        FactoryData result = underTest.findBuyable(DATA_ELEMENT);
        //THEN
        verify(dataQueryService).findBuyable(DATA_ELEMENT);
        assertEquals(armor, result);
    }
}