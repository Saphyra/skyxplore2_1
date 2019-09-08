package com.github.saphyra.skyxplore.game.gamecreator.gamecreator.factory.gameship.equipment.ai.item;

import com.github.saphyra.skyxplore.data.gamedata.GameDataConstants;
import com.github.saphyra.skyxplore.data.gamedata.GameDataFacade;
import com.github.saphyra.skyxplore.data.gamedata.domain.SlotType;
import com.github.saphyra.skyxplore.data.gamedata.entity.EquipmentDescription;
import com.github.saphyra.skyxplore.data.gamedata.entity.Extender;
import com.github.saphyra.skyxplore.data.gamedata.subservice.ExtenderService;
import com.github.saphyra.skyxplore.game.game.domain.ship.ShipEquipments;
import com.github.saphyra.skyxplore.game.gamecreator.gamecreator.factory.gameship.equipment.ai.domain.UpgradableSlot;
import com.github.saphyra.util.Random;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class RandomItemProviderTest {
    private static final String ITEM_ID = "item_id";
    private static final String CONNECTOR_ID = "connector_id";

    @Mock
    private ExtenderService extenderService;

    @Mock
    private GameDataFacade gameDataFacade;

    @Mock
    private Random random;

    @InjectMocks
    private RandomItemProvider underTest;

    @Mock
    private ShipEquipments shipEquipments;

    @Mock
    private EquipmentDescription itemEquipmentDescription;

    @Mock
    private EquipmentDescription connectorEquipmentDescription;

    @Mock
    private Extender extender;

    @Test
    public void getRandomItem() {
        //GIVEN
        given(gameDataFacade.getEquipmentDescriptionBySlotAndLevel(SlotType.CONNECTOR, 1)).willReturn(Arrays.asList(itemEquipmentDescription, connectorEquipmentDescription));
        given(itemEquipmentDescription.getId()).willReturn(ITEM_ID);
        given(connectorEquipmentDescription.getId()).willReturn(CONNECTOR_ID);
        given(shipEquipments.getConnectorEquipped()).willReturn(Arrays.asList(CONNECTOR_ID));

        given(extenderService.get(ITEM_ID)).willReturn(null);
        given(extenderService.get(CONNECTOR_ID)).willReturn(extender);

        given(extender.getExtendedSlot()).willReturn(GameDataConstants.CONNECTOR_SLOT_NAME);

        given(random.randInt(0, 0)).willReturn(0);
        //WHEN
        String result = underTest.getRandomItem(UpgradableSlot.CONNECTOR, shipEquipments);
        //THEN
        verify(random).randInt(0, 0);
        assertThat(result).isEqualTo(ITEM_ID);
    }
}