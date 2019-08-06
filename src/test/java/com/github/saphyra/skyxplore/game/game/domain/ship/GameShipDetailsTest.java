package com.github.saphyra.skyxplore.game.game.domain.ship;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class GameShipDetailsTest {
    private GameShipDetails underTest;

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

    @Before
    public void setUp() {
        underTest = GameShipDetails.builder()
            .coreHull(hullDetails)
            .defenseDetails(defenseDetails)
            .weaponDetails(weaponDetails)
            .energyDetails(energyDetails)
            .storageDetails(storageDetails)
            .abilityDetails(Arrays.asList(abilityDetails))
            .build();
    }

    @Test
    public void getAbilityDetails() {
        //WHEN
        List<AbilityDetails> result = underTest.getAbilityDetails();
        //THEN
        assertThat(result).containsExactly(abilityDetails);
        result.add(abilityDetails);
        assertThat(underTest.getAbilityDetails()).hasSize(1);
    }
}