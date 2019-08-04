package com.github.saphyra.skyxplore.game.gamecreator.gamecreator.factory.gameship.equipment.ai;

import com.github.saphyra.skyxplore.game.game.domain.ship.ShipEquipments;
import com.github.saphyra.skyxplore.game.gamecreator.gamecreator.factory.gameship.equipment.ai.domain.UpgradableSlot;
import com.github.saphyra.skyxplore.game.gamecreator.gamecreator.factory.gameship.equipment.ai.item.ItemProviderFacade;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class NewItemEquiperTest {
    private static final String ITEM_ID = "item_id";
    @Mock
    private ItemProviderFacade itemProviderFacade;

    @InjectMocks
    private NewItemEquiper underTest;

    @Mock
    private ShipEquipments shipEquipments;

    @Test
    public void equipNewItem() {
        //GIVEN
        given(itemProviderFacade.getRandomItem(UpgradableSlot.CONNECTOR, shipEquipments)).willReturn(ITEM_ID);
        ArrayList<String> equippedConnectors = new ArrayList<>();
        given(shipEquipments.getConnectorEquipped()).willReturn(equippedConnectors);
        //WHEN
        underTest.equipNewItem(shipEquipments, UpgradableSlot.CONNECTOR);
        //THEN
        assertThat(equippedConnectors).containsExactly(ITEM_ID);
    }
}