package org.github.saphyra.skyxplore.ship;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import org.github.saphyra.skyxplore.ship.domain.EquipRequest;
import org.github.saphyra.skyxplore.ship.domain.UnequipRequest;
import org.github.saphyra.skyxplore.ship.domain.ShipView;
import skyxplore.service.EquippedShipFacade;

@RunWith(MockitoJUnitRunner.class)
public class ShipControllerTest {
    private static final String CHARACTER_ID = "character_id";
    private static final String ITEM_ID = "item_id";
    private static final String EQUIP_TO = "equip_to";
    private static final String SLOT = "slot";

    @Mock
    private EquippedShipFacade equippedShipFacade;

    @InjectMocks
    private ShipController underTest;

    @Test
    public void testEquipShouldCallFacade() {
        //GIVEN
        EquipRequest equipRequest = new EquipRequest(ITEM_ID, EQUIP_TO);
        //WHEN
        underTest.equip(equipRequest, CHARACTER_ID);
        //THEN
        verify(equippedShipFacade).equip(equipRequest, CHARACTER_ID);
    }

    @Test
    public void testEquipShipShouldCallFacade() {
        //WHEN
        underTest.equipShip(CHARACTER_ID, ITEM_ID);
        //THEN
        verify(equippedShipFacade).equipShip(CHARACTER_ID, ITEM_ID);
    }

    @Test
    public void testGetShipDataShouldCallFacadeAndReturnView() {
        //GIVEN
        ShipView shipView = ShipView.builder().build();
        when(equippedShipFacade.getShipData(CHARACTER_ID)).thenReturn(shipView);
        //WHEN
        ShipView result = underTest.getShipData(CHARACTER_ID);
        //THEN
        assertEquals(shipView, result);
    }

    @Test
    public void testUnequipShouldCallFacade() {
        //GIVEN
        UnequipRequest unequipRequest = new UnequipRequest(SLOT, ITEM_ID);
        //WHEN
        underTest.unequip(unequipRequest, CHARACTER_ID);
        //THEN
        verify(equippedShipFacade).unequip(unequipRequest, CHARACTER_ID);
    }
}
