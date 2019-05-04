package org.github.saphyra.skyxplore.ship;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class EquippedShipFacadeTest {
    private static final String CHARACTER_ID = "character_id";

    @Mock
    private ShipCreatorService shipCreatorService;

    @InjectMocks
    private EquippedShipFacade underTest;

    @Test
    public void createShip(){
        //WHEN
        underTest.createShip(CHARACTER_ID);
        //THEN
        verify(shipCreatorService).createShip(CHARACTER_ID);
    }
}