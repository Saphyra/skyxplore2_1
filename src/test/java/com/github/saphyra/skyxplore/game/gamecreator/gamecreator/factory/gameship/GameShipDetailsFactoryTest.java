package com.github.saphyra.skyxplore.game.gamecreator.gamecreator.factory.gameship;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

import java.util.Arrays;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.github.saphyra.skyxplore.game.game.domain.ship.AbilityDetails;
import com.github.saphyra.skyxplore.game.game.domain.ship.DefenseDetails;
import com.github.saphyra.skyxplore.game.game.domain.ship.EnergyDetails;
import com.github.saphyra.skyxplore.game.game.domain.ship.GameShipDetails;
import com.github.saphyra.skyxplore.game.game.domain.ship.HullDetails;
import com.github.saphyra.skyxplore.game.game.domain.ship.ShipEquipments;
import com.github.saphyra.skyxplore.game.game.domain.ship.StorageDetails;
import com.github.saphyra.skyxplore.game.game.domain.ship.WeaponDetails;

@RunWith(MockitoJUnitRunner.class)
public class GameShipDetailsFactoryTest {
    @Mock
    private AbilityDetailsFactory abilityDetailsFactory;

    @Mock
    private CoreHullDetailsFactory coreHullDetailsFactory;

    @Mock
    private DefenseDetailsFactory defenseDetailsFactory;

    @Mock
    private EnergyDetailsFactory energyDetailsFactory;

    @Mock
    private StorageDetailsFactory storageDetailsFactory;

    @Mock
    private WeaponDetailsFactory weaponDetailsFactory;

    @InjectMocks
    private GameShipDetailsFactory underTest;

    @Mock
    private ShipEquipments shipEquipments;

    @Mock
    private HullDetails hullDetails;

    @Mock
    private DefenseDetails defenseDetails;

    @Mock
    private WeaponDetails weaponDetails;

    @Mock
    private EnergyDetails energyDetails;

    @Mock
    private StorageDetails storageDetails;

    @Mock
    private AbilityDetails abilityDetails;

    @Test
    public void create() {
        //GIVEN
        given(coreHullDetailsFactory.create(shipEquipments)).willReturn(hullDetails);
        given(defenseDetailsFactory.create(shipEquipments)).willReturn(defenseDetails);
        given(weaponDetailsFactory.create(shipEquipments)).willReturn(weaponDetails);
        given(energyDetailsFactory.create(shipEquipments)).willReturn(energyDetails);
        given(storageDetailsFactory.create(shipEquipments)).willReturn(storageDetails);
        given(abilityDetailsFactory.create(shipEquipments)).willReturn(Arrays.asList(abilityDetails));
        //WHEN
        GameShipDetails result = underTest.create(shipEquipments);
        //THEN
        assertThat(result.getCoreHull()).isEqualTo(hullDetails);
        assertThat(result.getDefenseDetails()).isEqualTo(defenseDetails);
        assertThat(result.getWeaponDetails()).isEqualTo(weaponDetails);
        assertThat(result.getEnergyDetails()).isEqualTo(energyDetails);
        assertThat(result.getStorageDetails()).isEqualTo(storageDetails);
        assertThat(result.getAbilityDetails()).containsExactly(abilityDetails);
    }
}