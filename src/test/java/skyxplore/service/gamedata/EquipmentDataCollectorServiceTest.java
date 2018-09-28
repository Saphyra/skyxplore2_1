package skyxplore.service.gamedata;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import skyxplore.dataaccess.gamedata.entity.Ship;
import skyxplore.dataaccess.gamedata.entity.abstractentity.GeneralDescription;
import skyxplore.dataaccess.gamedata.subservice.ShipService;
import skyxplore.domain.ship.EquippedShip;
import skyxplore.domain.slot.EquippedSlot;
import skyxplore.testutil.TestGeneralDescription;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static skyxplore.testutil.TestUtils.*;

@SuppressWarnings("ArraysAsListWithZeroOrOneArgument")
@RunWith(MockitoJUnitRunner.class)
public class EquipmentDataCollectorServiceTest {
    @Mock
    private DataQueryService dataQueryService;

    @Mock
    private ShipService shipService;

    @InjectMocks
    private EquipmentDataCollectorService underTest;

    @Test
    public void testCollectEquipmentDataShouldReturnList(){
        //GIVEN
        TestGeneralDescription generalDescription = new TestGeneralDescription();
        generalDescription.setId(DATA_ELEMENT);
        when(dataQueryService.getData(DATA_ELEMENT)).thenReturn(generalDescription);
        //WHEN
        Map<String, GeneralDescription> result = underTest.collectEquipmentData(Arrays.asList(DATA_ELEMENT));
        //THEN
        assertEquals(generalDescription, result.get(DATA_ELEMENT));
    }

    @Test
    public void testCollectEquipmentDataShouldReturnShip(){
        //GIVEN
        EquippedShip equippedShip = createEquippedShip();
        EquippedSlot defenseSlot = createEquippedDefenseSlot();
        EquippedSlot weaponSlot = createEquippedWeaponSlot();

        Ship ship = new Ship();
        ship.setId(EQUIPPED_SHIP_TYPE);
        ship.setAbility(new ArrayList<>(Arrays.asList(DATA_ABILITY)));

        when(shipService.get(EQUIPPED_SHIP_TYPE)).thenReturn(ship);

        GeneralDescription generalDescription = new TestGeneralDescription();
        generalDescription.setId(DATA_ELEMENT);
        when(dataQueryService.getData(anyString())).thenReturn(generalDescription);
        //WHEN
        Map<String, GeneralDescription> result = underTest.collectEquipmentData(equippedShip, defenseSlot, weaponSlot);
        //THEN
        verify(shipService).get(EQUIPPED_SHIP_TYPE);

        ArgumentCaptor<String> argumentCaptor = ArgumentCaptor.forClass(String.class);
        verify(dataQueryService, atLeastOnce()).getData(argumentCaptor.capture());
        assertTrue(argumentCaptor.getAllValues().contains(DATA_CONNECTOR));
        assertTrue(argumentCaptor.getAllValues().contains(DATA_ABILITY));
        assertTrue(argumentCaptor.getAllValues().contains(DATA_ITEM_FRONT));
        assertTrue(argumentCaptor.getAllValues().contains(DATA_ITEM_LEFT));
        assertTrue(argumentCaptor.getAllValues().contains(DATA_ITEM_RIGHT));
        assertTrue(argumentCaptor.getAllValues().contains(DATA_ITEM_BACK));

        assertTrue(result.containsKey(EQUIPPED_SHIP_TYPE));
        assertTrue(result.containsKey(DATA_ABILITY));
        assertTrue(result.containsKey(DATA_CONNECTOR));
        assertTrue(result.containsKey(DATA_ITEM_FRONT));
        assertTrue(result.containsKey(DATA_ITEM_LEFT));
        assertTrue(result.containsKey(DATA_ITEM_RIGHT));
        assertTrue(result.containsKey(DATA_ITEM_BACK));
    }
}