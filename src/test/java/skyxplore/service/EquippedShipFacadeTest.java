package skyxplore.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.github.saphyra.skyxplore.ship.domain.EquipRequest;
import org.github.saphyra.skyxplore.ship.domain.UnequipRequest;
import org.github.saphyra.skyxplore.ship.domain.ShipView;
import skyxplore.service.ship.EquipService;
import skyxplore.service.ship.EquipShipService;
import org.github.saphyra.skyxplore.ship.ShipQueryService;
import skyxplore.service.ship.UnequipService;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static skyxplore.testutil.TestUtils.CHARACTER_ID_1;
import static skyxplore.testutil.TestUtils.EQUIPPED_SHIP_ID;
import static skyxplore.testutil.TestUtils.createShipView;
import static skyxplore.testutil.TestUtils.createUnequipRequest;

@RunWith(MockitoJUnitRunner.class)
public class EquippedShipFacadeTest {
    @Mock
    private EquipService equipService;

    @Mock
    private EquipShipService equipShipService;

    @Mock
    private ShipQueryService shipQueryService;

    @Mock
    private UnequipService unequipService;

    @InjectMocks
    private EquippedShipFacade underTest;

    @Test
    public void testEquipShouldCallService() {
        //GIVEN
        EquipRequest request = new EquipRequest();
        //WHEN
        underTest.equip(request, CHARACTER_ID_1);
        //THEN
        verify(equipService).equip(request, CHARACTER_ID_1);
    }

    @Test
    public void testEquipShipShouldCallService() {
        //WHEN
        underTest.equipShip(CHARACTER_ID_1, EQUIPPED_SHIP_ID);
        //THEN
        verify(equipShipService).equipShip(CHARACTER_ID_1, EQUIPPED_SHIP_ID);
    }

    @Test
    public void testGetShiDataShouldCallServiceAndReturn() {
        //GIVEN
        ShipView shipView = createShipView();
        when(shipQueryService.getShipData(CHARACTER_ID_1)).thenReturn(shipView);
        //WHEN
        ShipView result = underTest.getShipData(CHARACTER_ID_1);
        //THEN
        verify(shipQueryService).getShipData(CHARACTER_ID_1);
        assertEquals(shipView, result);
    }

    @Test
    public void testUnequipShouldCallService() {
        //GIVEN
        UnequipRequest unequipRequest = createUnequipRequest();
        //WHEN
        underTest.unequip(unequipRequest, CHARACTER_ID_1);
        //THEN
        verify(unequipService).unequip(unequipRequest, CHARACTER_ID_1);
    }
}