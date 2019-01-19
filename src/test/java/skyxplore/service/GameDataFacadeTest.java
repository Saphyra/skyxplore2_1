package skyxplore.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import skyxplore.controller.request.character.EquipmentCategoryRequest;
import skyxplore.dataaccess.gamedata.entity.Armor;
import skyxplore.dataaccess.gamedata.entity.abstractentity.FactoryData;
import skyxplore.dataaccess.gamedata.entity.abstractentity.GeneralDescription;
import skyxplore.domain.ship.EquippedShip;
import skyxplore.domain.slot.EquippedSlot;
import skyxplore.service.gamedata.CategoryQueryService;
import skyxplore.service.gamedata.DataQueryService;
import skyxplore.service.gamedata.EquipmentDataCollectorService;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static skyxplore.testutil.TestUtils.*;

@SuppressWarnings("ArraysAsListWithZeroOrOneArgument")
@RunWith(MockitoJUnitRunner.class)
public class GameDataFacadeTest {
    @Mock
    private  CategoryQueryService categoryQueryService;

    @Mock
    private  DataQueryService dataQueryService;

    @Mock
    private  EquipmentDataCollectorService equipmentDataCollectorService;

    @InjectMocks
    private GameDataFacade underTest;

    @Test
    public void testGetDataShouldCallServiceAndReturn(){
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
    public void testCollectEquipmentDataListShouldCallServiceAndReturn(){
        //GIVEN
        List<String> param = Arrays.asList(DATA_ELEMENT);

        Map<String, GeneralDescription> generalDescriptionMap = createGeneralDescriptionMap();
        when(equipmentDataCollectorService.collectEquipmentData(param)).thenReturn(generalDescriptionMap);
        //WHEN
        Map<String, GeneralDescription> result = underTest.collectEquipmentData(param);
        //THEN
        verify(equipmentDataCollectorService).collectEquipmentData(param);
        assertEquals(generalDescriptionMap, result);
    }

    @Test
    public void testCollectEquipmentDataShipShouldCallServiceAndReturn(){
        //GIVEN
        EquippedShip ship = createEquippedShip();
        EquippedSlot defenseSlot = createEquippedDefenseSlot();
        EquippedSlot weaponSlot = createEquippedWeaponSlot();

        Map<String, GeneralDescription> generalDescriptionMap = createGeneralDescriptionMap();
        when(equipmentDataCollectorService.collectEquipmentData(ship, defenseSlot, weaponSlot)).thenReturn(generalDescriptionMap);
        //WHEN
        Map<String, GeneralDescription> result = underTest.collectEquipmentData(ship, defenseSlot, weaponSlot);
        //THEN
        verify(equipmentDataCollectorService).collectEquipmentData(ship, defenseSlot, weaponSlot);
        assertEquals(generalDescriptionMap, result);
    }

    @Test
    public void testGetFactoryDataShouldCallServiceAndReturn(){
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
    public void testGetElementsOfCategoryShouldCallServiceAndReturn(){
        //GIVEN
        Map<String, GeneralDescription> generalDescriptionMap = createGeneralDescriptionMap();
        when(categoryQueryService.getElementsOfCategory(EquipmentCategoryRequest.ALL)).thenReturn(generalDescriptionMap);
        //WHEN
        Map<String, GeneralDescription> result = underTest.getElementsOfCategory(EquipmentCategoryRequest.ALL);
        //THEN
        verify(categoryQueryService).getElementsOfCategory(EquipmentCategoryRequest.ALL);
        assertEquals(generalDescriptionMap, result);
    }

    @Test
    public void testFindBuyableShouldCallServiceAndReturn(){
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