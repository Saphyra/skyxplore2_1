package com.github.saphyra.skyxplore.data.subservice;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import com.github.saphyra.skyxplore.data.entity.Ship;

@RunWith(MockitoJUnitRunner.class)
public class ShipServiceTest {
    private static final String SHIP_ID_1 = "ship_id_1";
    private static final String SHIP_ID_2 = "ship_id_2";

    @InjectMocks
    private ShipService underTest;

    @Test
    public void getShipsByLevel() {
        //GIVEN
        Ship ship1 = new Ship();
        ship1.setId(SHIP_ID_1);
        ship1.setLevel(1);
        underTest.put(SHIP_ID_1, ship1);

        Ship ship2 = new Ship();
        ship2.setId(SHIP_ID_2);
        ship2.setLevel(2);
        underTest.put(SHIP_ID_2, ship2);
        //WHEN
        List<Ship> result = underTest.getShipsByLevel(2);
        //THEN
        assertThat(result).containsExactly(ship2);
    }
}