package com.github.saphyra.skyxplore.game.gamecreator.gamecreator.factory.gameship.equipment.ai.upgradeableitem;

import com.github.saphyra.skyxplore.game.game.domain.ship.ShipEquipments;
import com.github.saphyra.skyxplore.game.gamecreator.gamecreator.factory.gameship.equipment.ai.domain.UpgradableSlot;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class UpgradeableSlotProviderTest {
    @Mock
    private RandomEmptySlotProvider randomEmptySlotProvider;

    @Mock
    private RandomUpgradeableSlotProvider randomUpgradeableSlotProvider;

    @InjectMocks
    private UpgradeableSlotProvider underTest;

    @Mock
    private ShipEquipments shipEquipments;

    @Test
    public void getUpgradeableSlot_emptySlotPresent() {
        //GIVEN
        given(randomEmptySlotProvider.getRandomEmptySlot(shipEquipments)).willReturn(Optional.of(UpgradableSlot.CONNECTOR));
        //WHEN
        Optional<UpgradableSlot> result = underTest.getUpgradableSlot(shipEquipments);
        //THEN
        assertThat(result).contains(UpgradableSlot.CONNECTOR);
    }

    @Test
    public void getUpgradeableSlot_upgradeableSlotPresent() {
        //GIVEN
        given(randomEmptySlotProvider.getRandomEmptySlot(shipEquipments)).willReturn(Optional.empty());
        given(randomUpgradeableSlotProvider.getRandomUpgradeableSlot(shipEquipments)).willReturn(Optional.of(UpgradableSlot.CONNECTOR));
        //WHEN
        Optional<UpgradableSlot> result = underTest.getUpgradableSlot(shipEquipments);
        //THEN
        assertThat(result).contains(UpgradableSlot.CONNECTOR);
    }

    @Test
    public void getUpgradeableSlot_notFound() {
        //GIVEN
        given(randomEmptySlotProvider.getRandomEmptySlot(shipEquipments)).willReturn(Optional.empty());
        given(randomUpgradeableSlotProvider.getRandomUpgradeableSlot(shipEquipments)).willReturn(Optional.empty());
        //WHEN
        Optional<UpgradableSlot> result = underTest.getUpgradableSlot(shipEquipments);
        //THEN
        assertThat(result).isEmpty();
    }
}