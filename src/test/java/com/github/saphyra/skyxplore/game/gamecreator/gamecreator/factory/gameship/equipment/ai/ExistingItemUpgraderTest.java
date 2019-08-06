package com.github.saphyra.skyxplore.game.gamecreator.gamecreator.factory.gameship.equipment.ai;

import com.github.saphyra.skyxplore.game.game.domain.ship.ShipEquipments;
import com.github.saphyra.skyxplore.game.gamecreator.gamecreator.factory.gameship.equipment.ai.domain.UpgradableItem;
import com.github.saphyra.skyxplore.game.gamecreator.gamecreator.factory.gameship.equipment.ai.domain.UpgradableSlot;
import com.github.saphyra.skyxplore.game.gamecreator.gamecreator.factory.gameship.equipment.ai.item.ItemProviderFacade;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class ExistingItemUpgraderTest {
    private static final String ITEM_ID = "item_id";
    private static final String UPGRADED_ITEM_ID = "upgraded_item_id";

    @Mock
    private ItemProviderFacade itemProviderFacade;

    @InjectMocks
    private ExistingItemUpgrader underTest;

    private ShipEquipments shipEquipments = ShipEquipments.builder()
        .shipId(ITEM_ID)
        .connectorEquipped(new ArrayList<>(Arrays.asList(ITEM_ID)))
        .build();

    @Test
    public void upgradeExistingItem() {
        //GIVEN
        UpgradableItem upgradableItem = UpgradableItem.builder()
            .upgradeableItemId(Optional.of(ITEM_ID))
            .upgradableSlot(Optional.of(UpgradableSlot.CONNECTOR))
            .build();

        given(itemProviderFacade.getUpgradedVersionOf(ITEM_ID)).willReturn(Optional.of(UPGRADED_ITEM_ID));
        //WHEN
        underTest.upgradeExistingItem(shipEquipments, upgradableItem);
        //THEN
        assertThat(shipEquipments.getConnectorEquipped()).containsExactly(UPGRADED_ITEM_ID);
    }

    @Test(expected = RuntimeException.class)
    public void upgradeExistingItem_itemNotFound() {
        //GIVEN
        UpgradableItem upgradableItem = UpgradableItem.builder()
            .upgradeableItemId(Optional.of(ITEM_ID))
            .upgradableSlot(Optional.of(UpgradableSlot.CONNECTOR))
            .build();

        given(itemProviderFacade.getUpgradedVersionOf(ITEM_ID)).willReturn(Optional.empty());
        //WHEN
        underTest.upgradeExistingItem(shipEquipments, upgradableItem);
    }
}