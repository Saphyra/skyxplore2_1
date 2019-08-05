package com.github.saphyra.skyxplore.game.gamecreator.gamecreator.factory.gameship;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.github.saphyra.skyxplore.data.entity.Weapon;
import com.github.saphyra.skyxplore.data.subservice.WeaponService;
import com.github.saphyra.skyxplore.game.game.domain.ship.WeaponSideDetails;

@RunWith(MockitoJUnitRunner.class)
public class WeaponSideDetailsFactoryTest {
    private static final String WEAPON_ID = "weapon_id";
    private static final Integer ATTACK_SPEED = 235;
    private static final Integer RANGE = 364;
    private static final Integer CRITICAL_RATE = 87465;
    private static final Integer HULL_DAMAGE = 97;
    private static final Integer SHIELD_DAMAGE = 58624;
    private static final Integer ACCURACY = 4764;

    @Mock
    private WeaponService weaponService;

    @InjectMocks
    private WeaponSideDetailsFactory underTest;

    @Mock
    private Weapon weapon;

    @Test
    public void create() {
        //GIVEN
        given(weaponService.get(WEAPON_ID)).willReturn(weapon);

        given(weapon.getAttackSpeed()).willReturn(ATTACK_SPEED);
        given(weapon.getRange()).willReturn(RANGE);
        given(weapon.getCriticalRate()).willReturn(CRITICAL_RATE);
        given(weapon.getHullDamage()).willReturn(HULL_DAMAGE);
        given(weapon.getShieldDamage()).willReturn(SHIELD_DAMAGE);
        given(weapon.getAccuracy()).willReturn(ACCURACY);
        //WHEN
        List<WeaponSideDetails> result = underTest.create(Arrays.asList(WEAPON_ID));
        //THEN
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getItemId()).isEqualTo(WEAPON_ID);
        assertThat(result.get(0).getAttackSpeed()).isEqualTo(ATTACK_SPEED);
        assertThat(result.get(0).getRange()).isEqualTo(RANGE);
        assertThat(result.get(0).getCriticalRate()).isEqualTo(CRITICAL_RATE);
        assertThat(result.get(0).getHullDamage()).isEqualTo(HULL_DAMAGE);
        assertThat(result.get(0).getShieldDamage()).isEqualTo(SHIELD_DAMAGE);
        assertThat(result.get(0).getAccuracy()).isEqualTo(ACCURACY);
    }
}