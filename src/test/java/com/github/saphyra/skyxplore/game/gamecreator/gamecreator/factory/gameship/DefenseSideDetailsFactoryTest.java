package com.github.saphyra.skyxplore.game.gamecreator.gamecreator.factory.gameship;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

import java.util.Arrays;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.github.saphyra.skyxplore.game.game.domain.ship.DefenseSideDetails;
import com.github.saphyra.skyxplore.game.game.domain.ship.HullDetails;
import com.github.saphyra.skyxplore.game.game.domain.ship.ShieldDetails;

@RunWith(MockitoJUnitRunner.class)
public class DefenseSideDetailsFactoryTest {
    private static final String ITEM_ID = "item_id";

    @Mock
    private HullDetailsFactory hullDetailsFactory;

    @Mock
    private ShieldDetailsFactory shieldDetailsFactory;

    @InjectMocks
    private DefenseSideDetailsFactory underTest;

    @Mock
    private HullDetails hullDetails;

    @Mock
    private ShieldDetails shieldDetails;

    @Test
    public void create() {
        //GIVEN
        given(hullDetailsFactory.create(Arrays.asList(ITEM_ID))).willReturn(Arrays.asList(hullDetails));
        given(shieldDetailsFactory.create(Arrays.asList(ITEM_ID))).willReturn(Arrays.asList(shieldDetails));
        //WHEN
        DefenseSideDetails result = underTest.create(Arrays.asList(ITEM_ID));
        //THEN
        assertThat(result.getHulls()).containsExactly(hullDetails);
        assertThat(result.getShields()).containsExactly(shieldDetails);
    }
}