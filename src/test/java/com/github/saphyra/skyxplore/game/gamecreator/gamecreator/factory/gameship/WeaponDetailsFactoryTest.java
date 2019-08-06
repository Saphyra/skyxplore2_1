package com.github.saphyra.skyxplore.game.gamecreator.gamecreator.factory.gameship;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

import java.util.Arrays;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.github.saphyra.skyxplore.game.game.domain.ship.ShipEquipments;
import com.github.saphyra.skyxplore.game.game.domain.ship.WeaponDetails;
import com.github.saphyra.skyxplore.game.game.domain.ship.WeaponSideDetails;

@RunWith(MockitoJUnitRunner.class)
public class WeaponDetailsFactoryTest {
    private static final String FRONT_WEAPON_ID = "front-weapon-id";
    private static final String LEFT_WEAPON_ID = "left-weapon-id";
    private static final String RIGHT_WEAPON_ID = "right-weapon-id";
    private static final String BACK_WEAPON_ID = "back-weapon-id";

    @Mock
    private WeaponSideDetailsFactory weaponSideDetailsFactory;

    @InjectMocks
    private WeaponDetailsFactory underTest;

    @Mock
    private ShipEquipments shipEquipments;

    @Mock
    private WeaponSideDetails frontWeaponSideDetails;

    @Mock
    private WeaponSideDetails leftWeaponSideDetails;

    @Mock
    private WeaponSideDetails rightWeaponSideDetails;

    @Mock
    private WeaponSideDetails backWeaponSideDetails;

    @Test
    public void create() {
        //GIVEN
        given(shipEquipments.getFrontWeapon()).willReturn(Arrays.asList(FRONT_WEAPON_ID));
        given(weaponSideDetailsFactory.create(Arrays.asList(FRONT_WEAPON_ID))).willReturn(Arrays.asList(frontWeaponSideDetails));

        given(shipEquipments.getLeftWeapon()).willReturn(Arrays.asList(LEFT_WEAPON_ID));
        given(weaponSideDetailsFactory.create(Arrays.asList(LEFT_WEAPON_ID))).willReturn(Arrays.asList(leftWeaponSideDetails));

        given(shipEquipments.getRightWeapon()).willReturn(Arrays.asList(RIGHT_WEAPON_ID));
        given(weaponSideDetailsFactory.create(Arrays.asList(RIGHT_WEAPON_ID))).willReturn(Arrays.asList(rightWeaponSideDetails));

        given(shipEquipments.getBackWeapon()).willReturn(Arrays.asList(BACK_WEAPON_ID));
        given(weaponSideDetailsFactory.create(Arrays.asList(BACK_WEAPON_ID))).willReturn(Arrays.asList(backWeaponSideDetails));
        //WHEN
        WeaponDetails result = underTest.create(shipEquipments);
        //THEN
        assertThat(result.getFrontWeapon()).containsExactly(frontWeaponSideDetails);
        assertThat(result.getLeftWeapon()).containsExactly(leftWeaponSideDetails);
        assertThat(result.getRightWeapon()).containsExactly(rightWeaponSideDetails);
        assertThat(result.getBackWeapon()).containsExactly(backWeaponSideDetails);
    }
}