package com.github.saphyra.skyxplore.game.gamecreator.gamecreator.factory.gameship.equipment.ai.item;

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
public class ItemProviderFacadeTest {
    private static final String ITEM_ID = "item_id";
    private static final String UPGRADED_ITEM_ID = "upgraded_item_id";

    @Mock
    private RandomItemProvider randomItemProvider;

    @Mock
    private ShipProvider shipProvider;

    @Mock
    private UpgradedItemProvider upgradedItemProvider;

    @InjectMocks
    private ItemProviderFacade underTest;

    @Mock
    private ShipEquipments shipEquipments;

    @Test
    public void getRandomShip() {
        //GIVEN
        given(shipProvider.getRandomShip()).willReturn(ITEM_ID);
        //WHEN
        String result = underTest.getRandomShip();
        //THEN
        assertThat(result).isEqualTo(ITEM_ID);
    }

    @Test
    public void getRandomItem() {
        //GIVEN
        given(randomItemProvider.getRandomItem(UpgradableSlot.CONNECTOR, shipEquipments)).willReturn(ITEM_ID);
        //WHEN
        String result = underTest.getRandomItem(UpgradableSlot.CONNECTOR, shipEquipments);
        //THEN
        assertThat(result).isEqualTo(ITEM_ID);
    }

    @Test
    public void getUpgradedVersionOf() {
        //GIVEN
        given(upgradedItemProvider.getUpgradedVersionOf(ITEM_ID)).willReturn(Optional.of(UPGRADED_ITEM_ID));
        //WHEN
        Optional<String> result = underTest.getUpgradedVersionOf(ITEM_ID);
        //THEN
        assertThat(result).contains(UPGRADED_ITEM_ID);
    }
}