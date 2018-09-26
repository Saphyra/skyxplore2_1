package skyxplore.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static skyxplore.testutil.TestUtils.CHARACTER_ID_1;
import static skyxplore.testutil.TestUtils.EQUIPPED_SHIP_ID;
import static skyxplore.testutil.TestUtils.createUnequipRequest;

import java.util.HashMap;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import skyxplore.controller.request.character.EquipRequest;
import skyxplore.controller.request.character.UnequipRequest;
import skyxplore.controller.view.View;
import skyxplore.controller.view.ship.ShipView;
import skyxplore.service.ship.EquipService;
import skyxplore.service.ship.EquipShipService;
import skyxplore.service.ship.ShipQueryService;
import skyxplore.service.ship.UnequipService;

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
        View<ShipView> view = new View<ShipView>(new ShipView(), new HashMap<>());
        when(shipQueryService.getShipData(CHARACTER_ID_1)).thenReturn(view);
        //WHEN
        View<ShipView> result = underTest.getShipData(CHARACTER_ID_1);
        //THEN
        verify(shipQueryService).getShipData(CHARACTER_ID_1);
        assertEquals(view, result);
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