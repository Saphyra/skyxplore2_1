package com.github.saphyra.skyxplore.game.gamecreator.gamecreator.factory.gameship.equipment.ai.item;

import com.github.saphyra.skyxplore.data.GameDataFacade;
import com.github.saphyra.skyxplore.data.entity.EquipmentDescription;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class UpgradedItemProviderTest {
    private static final String ITEM_ID = "item_id-01";
    private static final String UPGRADED_ITEM_ID = "item_id-02";
    @Mock
    private GameDataFacade gameDataFacade;

    @InjectMocks
    private UpgradedItemProvider underTest;

    @Mock
    private EquipmentDescription equipmentDescription;

    @Mock
    private EquipmentDescription upgradedEquipmentDescription;

    @Test
    public void getUpgradedVersionOf_found() {
        //GIVEN
        given(gameDataFacade.findEquipmentDescription(ITEM_ID)).willReturn(equipmentDescription);
        given(equipmentDescription.getLevel()).willReturn(1);
        given(gameDataFacade.findEquipmentDescription(UPGRADED_ITEM_ID)).willReturn(upgradedEquipmentDescription);
        given(upgradedEquipmentDescription.getId()).willReturn(UPGRADED_ITEM_ID);
        //WHEN
        Optional<String> result = underTest.getUpgradedVersionOf(ITEM_ID);
        //THEN
        assertThat(result).contains(UPGRADED_ITEM_ID);
    }

    @Test
    public void getUpgradedVersionOf_notFound() {
        //GIVEN
        given(gameDataFacade.findEquipmentDescription(ITEM_ID)).willReturn(equipmentDescription);
        given(equipmentDescription.getLevel()).willReturn(1);
        given(gameDataFacade.findEquipmentDescription(UPGRADED_ITEM_ID)).willReturn(null);
        //WHEN
        Optional<String> result = underTest.getUpgradedVersionOf(ITEM_ID);
        //THEN
        assertThat(result).isEmpty();
    }
}