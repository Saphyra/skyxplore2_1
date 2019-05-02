package org.github.saphyra.skyxplore.ship;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.github.saphyra.skyxplore.ship.domain.EquipRequest;
import org.github.saphyra.skyxplore.ship.domain.ShipView;
import org.github.saphyra.skyxplore.ship.domain.UnequipRequest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import skyxplore.service.ship.EquipService;
import skyxplore.service.ship.EquipShipService;
import skyxplore.service.ship.UnequipService;

@RunWith(MockitoJUnitRunner.class)
public class EquippedShipFacadeTest {
    private static final String CHARACTER_ID = "character_id";
    private static final String EQUIPPED_SHIP_ID = "equipped_ship_id";
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
        underTest.equip(request, CHARACTER_ID);
        //THEN
        verify(equipService).equip(request, CHARACTER_ID);
    }

    @Test
    public void testEquipShipShouldCallService() {
        //WHEN
        underTest.equipShip(CHARACTER_ID, EQUIPPED_SHIP_ID);
        //THEN
        verify(equipShipService).equipShip(CHARACTER_ID, EQUIPPED_SHIP_ID);
    }

    @Test
    public void testGetShiDataShouldCallServiceAndReturn() {
        //GIVEN
        ShipView shipView = ShipView.builder().build();
        when(shipQueryService.getShipData(CHARACTER_ID)).thenReturn(shipView);
        //WHEN
        ShipView result = underTest.getShipData(CHARACTER_ID);
        //THEN
        verify(shipQueryService).getShipData(CHARACTER_ID);
        assertThat(result).isEqualTo(shipView);
    }

    @Test
    public void testUnequipShouldCallService() {
        //GIVEN
        UnequipRequest unequipRequest = new UnequipRequest();
        //WHEN
        underTest.unequip(unequipRequest, CHARACTER_ID);
        //THEN
        verify(unequipService).unequip(unequipRequest, CHARACTER_ID);
    }
}