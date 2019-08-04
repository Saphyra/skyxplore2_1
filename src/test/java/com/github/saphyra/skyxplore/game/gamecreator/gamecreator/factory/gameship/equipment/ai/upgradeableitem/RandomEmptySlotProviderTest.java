package com.github.saphyra.skyxplore.game.gamecreator.gamecreator.factory.gameship.equipment.ai.upgradeableitem;

import com.github.saphyra.skyxplore.game.game.domain.ship.ShipEquipments;
import com.github.saphyra.skyxplore.game.gamecreator.GameCreatorContext;
import com.github.saphyra.skyxplore.game.gamecreator.gamecreator.factory.gameship.equipment.ai.domain.UpgradableSlot;
import com.github.saphyra.util.Random;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class RandomEmptySlotProviderTest {
    @Mock
    private GameCreatorContext gameCreatorContext;

    @Mock
    private Random random;

    private RandomEmptySlotProvider underTest;

    @Mock
    private ShipEquipments shipEquipments;

    @Test
    public void getRandomEmptySlot_found() {
        //GIVEN
        underTest = RandomEmptySlotProvider.builder()
            .gameCreatorContext(gameCreatorContext)
            .random(random)
            .upgradableSlots(Arrays.asList(UpgradableSlot.TEST_HAS_EMPTY_SLOT))
            .build();
        //WHEN
        Optional<UpgradableSlot> result = underTest.getRandomEmptySlot(shipEquipments);
        //THEN
        assertThat(result).contains(UpgradableSlot.TEST_HAS_EMPTY_SLOT);
    }

    @Test
    public void getRandomEmptySlot_notFound() {
        //GIVEN
        underTest = RandomEmptySlotProvider.builder()
            .gameCreatorContext(gameCreatorContext)
            .random(random)
            .upgradableSlots(Arrays.asList(UpgradableSlot.TEST_HAS_NOT_EMPTY_SLOT))
            .build();
        //WHEN
        Optional<UpgradableSlot> result = underTest.getRandomEmptySlot(shipEquipments);
        //THEN
        assertThat(result).isEmpty();
    }
}