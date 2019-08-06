package com.github.saphyra.skyxplore.game.gamecreator.gamecreator.factory.gameship.equipment.ai.upgradeableitem;

import com.github.saphyra.skyxplore.game.game.domain.ship.ShipEquipments;
import com.github.saphyra.skyxplore.game.gamecreator.gamecreator.factory.gameship.equipment.ai.domain.UpgradableItem;
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
public class UpgradeableItemProviderTest {
    private static final String ITEM_ID = "item_id";

    @Mock
    private UpgradeableItemIdProvider upgradeableItemIdProvider;

    @Mock
    private UpgradeableSlotProvider upgradeableSlotProvider;

    @InjectMocks
    private UpgradeableItemProvider underTest;

    @Mock
    private ShipEquipments shipEquipments;

    @Test
    public void getUpgradableItem() {
        //GIVEN
        given(upgradeableSlotProvider.getUpgradableSlot(shipEquipments)).willReturn(Optional.of(UpgradableSlot.CONNECTOR));
        given(upgradeableItemIdProvider.getUpgradableItemId(Optional.of(UpgradableSlot.CONNECTOR), shipEquipments)).willReturn(Optional.of(ITEM_ID));
        //WHEN
        UpgradableItem result = underTest.getUpgradableItem(shipEquipments);
        //THEN
        assertThat(result.getUpgradableSlot()).contains(UpgradableSlot.CONNECTOR);
        assertThat(result.getUpgradeableItemId()).contains(ITEM_ID);
    }
}