package com.github.saphyra.skyxplore.game.gamecreator.gamecreator.factory.gameship;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.mockito.BDDMockito.given;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.github.saphyra.skyxplore.data.entity.Shield;
import com.github.saphyra.skyxplore.data.subservice.ShieldService;
import com.github.saphyra.skyxplore.game.game.domain.ship.ShieldDetails;

@RunWith(MockitoJUnitRunner.class)
public class ShieldDetailsFactoryTest {
    private static final String ITEM_ID = "item_id";
    private static final Integer CAPACITY = 2345;
    private static final Integer REGENERATION = 45;
    private static final Integer ENERGY_USAGE = 547;

    @Mock
    private ShieldService shieldService;

    @InjectMocks
    private ShieldDetailsFactory underTest;

    @Mock
    private Shield shield;

    @Test
    public void create() {
        //GIVEN
        given(shieldService.get(ITEM_ID)).willReturn(shield);
        given(shield.getCapacity()).willReturn(CAPACITY);
        given(shield.getRegeneration()).willReturn(REGENERATION);
        given(shield.getEnergyUsage()).willReturn(ENERGY_USAGE);
        //WHEN
        List<ShieldDetails> result = underTest.create(Arrays.asList(ITEM_ID));
        //THEN
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getItemId()).isEqualTo(ITEM_ID);
        assertThat(result.get(0).getMaxShield()).isEqualTo(CAPACITY);
        assertThat(result.get(0).getActualShield()).isEqualTo(CAPACITY);
        assertThat(result.get(0).getRecharge()).isEqualTo(REGENERATION);
        assertThat(result.get(0).getEnergyUsage()).isEqualTo(ENERGY_USAGE);
    }
}