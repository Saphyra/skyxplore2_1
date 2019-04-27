package skyxplore.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import skyxplore.controller.request.character.EquipRequest;
import skyxplore.controller.request.character.UnequipRequest;
import skyxplore.controller.view.ship.ShipView;
import skyxplore.service.EquippedShipFacade;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static skyxplore.testutil.TestUtils.CHARACTER_ID_1;
import static skyxplore.testutil.TestUtils.EQUIP_ITEM_ID;
import static skyxplore.testutil.TestUtils.createEquipRequest;
import static skyxplore.testutil.TestUtils.createShipView;
import static skyxplore.testutil.TestUtils.createUnequipRequest;

@RunWith(MockitoJUnitRunner.class)
public class ShipControllerTest {
    @Mock
    private EquippedShipFacade equippedShipFacade;

    @InjectMocks
    private ShipController underTest;

    @Test
    public void testEquipShouldCallFacade() {
        //GIVEN
        EquipRequest equipRequest = createEquipRequest();
        //WHEN
        underTest.equip(equipRequest, CHARACTER_ID_1);
        //THEN
        verify(equippedShipFacade).equip(equipRequest, CHARACTER_ID_1);
    }

    @Test
    public void testEquipShipShouldCallFacade() {
        //WHEN
        underTest.equipShip(CHARACTER_ID_1, EQUIP_ITEM_ID);
        //THEN
        verify(equippedShipFacade).equipShip(CHARACTER_ID_1, EQUIP_ITEM_ID);
    }

    @Test
    public void testGetShipDataShouldCallFacadeAndReturnView() {
        //GIVEN
        ShipView shipView = createShipView();
        when(equippedShipFacade.getShipData(CHARACTER_ID_1)).thenReturn(shipView);
        //WHEN
        ShipView result = underTest.getShipData(CHARACTER_ID_1);
        //THEN
        assertEquals(shipView, result);
    }

    @Test
    public void testUnequipShouldCallFacade() {
        //GIVEN
        UnequipRequest unequipRequest = createUnequipRequest();
        //WHEN
        underTest.unequip(unequipRequest, CHARACTER_ID_1);
        //THEN
        verify(equippedShipFacade).unequip(unequipRequest, CHARACTER_ID_1);
    }
}