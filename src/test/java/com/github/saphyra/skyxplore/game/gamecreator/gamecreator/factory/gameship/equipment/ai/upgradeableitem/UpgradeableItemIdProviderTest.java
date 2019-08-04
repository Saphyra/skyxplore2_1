package com.github.saphyra.skyxplore.game.gamecreator.gamecreator.factory.gameship.equipment.ai.upgradeableitem;

import com.github.saphyra.skyxplore.game.game.domain.ship.ShipEquipments;
import com.github.saphyra.skyxplore.game.gamecreator.GameCreatorContext;
import com.github.saphyra.skyxplore.game.gamecreator.gamecreator.factory.gameship.equipment.ai.domain.UpgradableSlot;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class UpgradeableItemIdProviderTest {
    private static final String ITEM_ID = "item_id";
    private static final String UPGRADEABLE_ITEM_ID = "upgradeable_item_id";

    @Mock
    private GameCreatorContext gameCreatorContext;

    @Mock
    private RandomUpgradeableItemOfSlotProvider randomUpgradeableItemOfSlotProvider;

    @InjectMocks
    private UpgradeableItemIdProvider underTest;

    @Mock
    private ShipEquipments shipEquipments;

    @Test
    public void getUpgradableItemId_found() {
        //GIVEN
        given(shipEquipments.getConnectorEquipped()).willReturn(Arrays.asList(ITEM_ID));
        given(randomUpgradeableItemOfSlotProvider.getRandomUpgradableItemOfSlot(Arrays.asList(ITEM_ID))).willReturn(Optional.of(UPGRADEABLE_ITEM_ID));
        //WHEN
        Optional<String> result = underTest.getUpgradableItemId(Optional.of(UpgradableSlot.TEST_HAS_NOT_EMPTY_SLOT), shipEquipments);
        //THEN
        assertThat(result).contains(UPGRADEABLE_ITEM_ID);
    }

    @Test
    public void getUpgradableItemId_upgradeableItemNotFound() {
        //GIVEN
        given(shipEquipments.getConnectorEquipped()).willReturn(Arrays.asList(ITEM_ID));
        given(randomUpgradeableItemOfSlotProvider.getRandomUpgradableItemOfSlot(Arrays.asList(ITEM_ID))).willReturn(Optional.empty());
        //WHEN
        Optional<String> result = underTest.getUpgradableItemId(Optional.of(UpgradableSlot.TEST_HAS_NOT_EMPTY_SLOT), shipEquipments);
        //THEN
        assertThat(result).isEmpty();
    }

    @Test
    public void getUpgradableItemId_upgradeableSlot() {
        //WHEN
        Optional<String> result = underTest.getUpgradableItemId(Optional.empty(), shipEquipments);
        //THEN
        assertThat(result).isEmpty();
    }

    @Test
    public void getUpgradableItemId_hasEmptySlot() {
        //WHEN
        Optional<String> result = underTest.getUpgradableItemId(Optional.of(UpgradableSlot.TEST_HAS_EMPTY_SLOT), shipEquipments);
        //THEN
        assertThat(result).isEmpty();
    }
}