package com.github.saphyra.skyxplore.game.gamecreator.gamecreator.factory.gameship;

import com.github.saphyra.skyxplore.data.gamedata.entity.Ability;
import com.github.saphyra.skyxplore.data.gamedata.entity.Ship;
import com.github.saphyra.skyxplore.data.gamedata.subservice.AbilityService;
import com.github.saphyra.skyxplore.data.gamedata.subservice.ShipService;
import com.github.saphyra.skyxplore.game.game.domain.ship.AbilityDetails;
import com.github.saphyra.skyxplore.game.game.domain.ship.ShipEquipments;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class AbilityDetailsFactoryTest {
    private static final String SHIP_ID = "ship-id";
    private static final String ABILITY_ID = "ability-id";
    private static final Integer ENERGY_USAGE = 26;
    private static final Integer RELOAD = 21;
    private static final Integer ACTIVE = 32;
    private static final String EFFECT_KEY = "effect-key";
    private static final Integer EFFECT_VALUE = 3241;

    @Mock
    private AbilityService abilityService;

    @Mock
    private ShipService shipService;

    @InjectMocks
    private AbilityDetailsFactory underTest;

    @Mock
    private ShipEquipments shipEquipments;

    @Mock
    private Ship ship;

    @Mock
    private Ability ability;

    @Before
    public void setUp() {
        given(shipEquipments.getShipId()).willReturn(SHIP_ID);
        given(shipService.getOptional(SHIP_ID)).willReturn(Optional.of(ship));
        given(ship.getAbility()).willReturn(Arrays.asList(ABILITY_ID));
        given(abilityService.getOptional(ABILITY_ID)).willReturn(Optional.of(ability));

        given(ability.getId()).willReturn(ABILITY_ID);
        given(ability.getEnergyUsage()).willReturn(ENERGY_USAGE);
        given(ability.getReload()).willReturn(RELOAD);
        given(ability.getActive()).willReturn(ACTIVE);
    }

    @Test(expected = RuntimeException.class)
    public void create_shipNotFound() {
        //GIVEN
        given(shipService.getOptional(SHIP_ID)).willReturn(Optional.empty());
        //WHEN
        underTest.create(shipEquipments);
    }

    @Test(expected = RuntimeException.class)
    public void create_abilityNotFound() {
        //GIVEN
        given(abilityService.getOptional(ABILITY_ID)).willReturn(Optional.empty());
        //WHEN
        underTest.create(shipEquipments);
    }

    @Test
    public void create_nullEffect() {
        //GIVEN
        given(ability.getEffect()).willReturn(null);
        //WHEN
        List<AbilityDetails> result = underTest.create(shipEquipments);
        //THEN
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getItemId()).isEqualTo(ABILITY_ID);
        assertThat(result.get(0).getEnergyUsage()).isEqualTo(ENERGY_USAGE);
        assertThat(result.get(0).getReload()).isEqualTo(RELOAD);
        assertThat(result.get(0).getActive()).isEqualTo(ACTIVE);
        assertThat(result.get(0).getData()).hasSize(0);
    }

    @Test
    public void create() {
        //GIVEN
        Map<String, Integer> effect = new HashMap<>();
        effect.put(EFFECT_KEY, EFFECT_VALUE);
        given(ability.getEffect()).willReturn(effect);
        //WHEN
        List<AbilityDetails> result = underTest.create(shipEquipments);
        //THEN
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getItemId()).isEqualTo(ABILITY_ID);
        assertThat(result.get(0).getEnergyUsage()).isEqualTo(ENERGY_USAGE);
        assertThat(result.get(0).getReload()).isEqualTo(RELOAD);
        assertThat(result.get(0).getActive()).isEqualTo(ACTIVE);
        assertThat(result.get(0).getData().get(EFFECT_KEY)).isEqualTo(EFFECT_VALUE);
    }
}