package com.github.saphyra.skyxplore.game.game.domain.ship;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class AbilityDetailsTest {
    private static final String ITEM_ID = "item_id";
    private static final int ENERGY_USAGE = 23;
    private static final int RELOAD = 324;
    private static final int ACTIVE = 2332;
    private static final String DATA_KEY = "data-key";
    private static final Integer DATA_VALUE = 42565;

    private AbilityDetails underTest;

    @Before
    public void setUp() {
        Map<String, Integer> data = new HashMap<>();
        data.put(DATA_KEY, DATA_VALUE);

        underTest = AbilityDetails.builder()
            .itemId(ITEM_ID)
            .energyUsage(ENERGY_USAGE)
            .reload(RELOAD)
            .active(ACTIVE)
            .data(data)
            .build();
    }

    @Test
    public void getData() {
        //WHEN
        Map<String, Integer> result = underTest.getData();
        //THEN
        assertThat(result.get(DATA_KEY)).isEqualTo(DATA_VALUE);
        result.put(DATA_KEY, RELOAD);
        assertThat(underTest.getData().get(DATA_KEY)).isEqualTo(DATA_VALUE);
    }

}