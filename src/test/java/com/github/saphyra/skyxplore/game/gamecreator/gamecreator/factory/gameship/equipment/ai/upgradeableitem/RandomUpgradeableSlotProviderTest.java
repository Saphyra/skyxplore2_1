package com.github.saphyra.skyxplore.game.gamecreator.gamecreator.factory.gameship.equipment.ai.upgradeableitem;

import com.github.saphyra.skyxplore.game.game.domain.ship.ShipEquipments;
import com.github.saphyra.skyxplore.game.gamecreator.gamecreator.factory.gameship.equipment.ai.domain.UpgradableSlot;
import com.github.saphyra.util.Random;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class RandomUpgradeableSlotProviderTest {
    private static final String ITEM_ID = "item_id";

    @Mock
    private Random random;

    @Mock
    private RandomUpgradeableItemOfSlotProvider randomUpgradeableItemOfSlotProvider;

    private RandomUpgradeableSlotProvider underTest;

    @Mock
    private ShipEquipments shipEquipments;

    @Before
    public void setUp() {
        underTest = RandomUpgradeableSlotProvider.builder()
            .random(random)
            .randomUpgradeableItemOfSlotProvider(randomUpgradeableItemOfSlotProvider)
            .upgradableSlots(Arrays.asList(UpgradableSlot.CONNECTOR))
            .build();

    }

    @Test
    public void getRandomUpgradeableSlot_found() {
        //GIVEN
        given(shipEquipments.getConnectorEquipped()).willReturn(Arrays.asList(ITEM_ID));
        given(randomUpgradeableItemOfSlotProvider.getRandomUpgradableItemOfSlot(Arrays.asList(ITEM_ID))).willReturn(Optional.of(ITEM_ID));
        //WHEN
        Optional<UpgradableSlot> result = underTest.getRandomUpgradeableSlot(shipEquipments);
        //THEN
        assertThat(result).contains(UpgradableSlot.CONNECTOR);
    }

    @Test
    public void getRandomUpgradeableSlot_notFound() {
        //GIVEN
        given(shipEquipments.getConnectorEquipped()).willReturn(Arrays.asList(ITEM_ID));
        given(randomUpgradeableItemOfSlotProvider.getRandomUpgradableItemOfSlot(Arrays.asList(ITEM_ID))).willReturn(Optional.empty());
        //WHEN
        Optional<UpgradableSlot> result = underTest.getRandomUpgradeableSlot(shipEquipments);
        //THEN
        assertThat(result).isEmpty();
    }
}