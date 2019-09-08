package com.github.saphyra.skyxplore.game.gamecreator.gamecreator.factory.gameship.equipment.ai.item;

import com.github.saphyra.skyxplore.data.gamedata.entity.Ship;
import com.github.saphyra.skyxplore.data.gamedata.subservice.ShipService;
import com.github.saphyra.util.Random;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class ShipProviderTest {
    private static final String SHIP_ID = "ship_id";

    @Mock
    private Random random;

    @Mock
    private ShipService shipService;

    @InjectMocks
    private ShipProvider underTest;

    @Mock
    private Ship ship;

    @Test
    public void getRandomShip() {
        //GIVEN
        given(shipService.getShipsByLevel(1)).willReturn(Arrays.asList(ship));
        given(ship.getId()).willReturn(SHIP_ID);
        given(random.randInt(0, 0)).willReturn(0);
        //WHEN
        String result = underTest.getRandomShip();
        //THEN
        assertThat(result).isEqualTo(SHIP_ID);
    }
}