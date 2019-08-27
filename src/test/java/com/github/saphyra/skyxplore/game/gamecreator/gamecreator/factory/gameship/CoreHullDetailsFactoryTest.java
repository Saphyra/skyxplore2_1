package com.github.saphyra.skyxplore.game.gamecreator.gamecreator.factory.gameship;

import com.github.saphyra.skyxplore.data.gamedata.entity.CoreHull;
import com.github.saphyra.skyxplore.data.gamedata.entity.Ship;
import com.github.saphyra.skyxplore.data.gamedata.subservice.CoreHullService;
import com.github.saphyra.skyxplore.data.gamedata.subservice.ShipService;
import com.github.saphyra.skyxplore.game.game.domain.ship.HullDetails;
import com.github.saphyra.skyxplore.game.game.domain.ship.ShipEquipments;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class CoreHullDetailsFactoryTest {
    private static final String SHIP_ID = "ship-id";
    private static final String CORE_HULL_ID = "core-hull-id";
    private static final Integer CORE_HULL_BASE = 1000;
    private static final Integer CAPACITY = 161;
    private static final String ITEM_ID = "item-id";

    @Mock
    private CoreHullService coreHullService;

    @Mock
    private ShipService shipService;

    @InjectMocks
    private CoreHullDetailsFactory underTest;

    @Mock
    private ShipEquipments shipEquipments;

    @Mock
    private Ship ship;

    @Mock
    private CoreHull coreHull;

    @Before
    public void setUp() {
        given(shipEquipments.getShipId()).willReturn(SHIP_ID);
        given(shipEquipments.getConnectorEquipped()).willReturn(Arrays.asList(CORE_HULL_ID, ITEM_ID));
    }

    @Test(expected = RuntimeException.class)
    public void create_shipNotFound() {
        //GIVEN
        given(shipService.get(SHIP_ID)).willReturn(null);
        //WHEN
        underTest.create(shipEquipments);
    }

    @Test
    public void create() {
        //GIVEN
        given(shipService.get(SHIP_ID)).willReturn(ship);
        given(ship.getCoreHull()).willReturn(CORE_HULL_BASE);
        given(coreHullService.get(CORE_HULL_ID)).willReturn(coreHull);
        given(coreHull.getCapacity()).willReturn(CAPACITY);
        //WHEN
        HullDetails result = underTest.create(shipEquipments);
        //THEN
        assertThat(result.getItemId()).isEqualTo(SHIP_ID);
        assertThat(result.getMaxHull()).isEqualTo(CORE_HULL_BASE + CAPACITY);
        assertThat(result.getActualHull()).isEqualTo(CORE_HULL_BASE + CAPACITY);
    }
}