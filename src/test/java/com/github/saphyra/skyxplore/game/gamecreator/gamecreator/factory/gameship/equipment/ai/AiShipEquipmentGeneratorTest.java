package com.github.saphyra.skyxplore.game.gamecreator.gamecreator.factory.gameship.equipment.ai;

import com.github.saphyra.skyxplore.game.game.domain.ship.GameShip;
import com.github.saphyra.skyxplore.game.game.domain.ship.ShipEquipments;
import com.github.saphyra.skyxplore.game.gamecreator.gamecreator.factory.gameship.equipment.ai.domain.UpgradableItem;
import com.github.saphyra.skyxplore.game.gamecreator.gamecreator.factory.gameship.equipment.ai.domain.UpgradableSlot;
import com.github.saphyra.skyxplore.game.gamecreator.gamecreator.factory.gameship.equipment.ai.item.ItemProviderFacade;
import com.github.saphyra.skyxplore.game.gamecreator.gamecreator.factory.gameship.equipment.ai.upgradeableitem.UpgradeableItemProvider;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class AiShipEquipmentGeneratorTest {
    private static final String SHIP_ID = "ship_id";
    private static final String ITEM_ID = "item_id";
    private static final String UPGRADED_SHIP_ID = "upgraded_ship_id";

    @Mock
    private ExistingItemUpgrader existingItemUpgrader;

    @Mock
    private ItemProviderFacade itemProviderFacade;

    @Mock
    private NewItemEquiper newItemEquiper;

    @Mock
    private PointCalculator pointCalculator;

    @Mock
    private TargetPointCalculator targetPointCalculator;

    @Mock
    private UpgradeableItemProvider upgradeableItemProvider;

    @InjectMocks
    private AiShipEquipmentGenerator underTest;

    @Mock
    private GameShip gameShip;

    @Before
    public void setUp() {
        given(targetPointCalculator.getTargetPoints(Arrays.asList(gameShip))).willReturn(2);
        given(pointCalculator.countPointsOfEquipments(any()))
            .willReturn(1)
            .willReturn(2);

        given(itemProviderFacade.getRandomShip()).willReturn(SHIP_ID);
    }

    @Test
    public void generateEquipments_upgradeExistingItem() {
        //GIVEN
        UpgradableItem upgradableItem = UpgradableItem.builder()
            .upgradableSlot(Optional.of(UpgradableSlot.CONNECTOR))
            .upgradeableItemId(Optional.of(ITEM_ID))
            .build();
        given(upgradeableItemProvider.getUpgradableItem(any())).willReturn(upgradableItem);
        //WHEN
        ShipEquipments result = underTest.generateEquipments(Arrays.asList(gameShip));
        //THEN
        verify(existingItemUpgrader).upgradeExistingItem(any(), eq(upgradableItem));
        assertThat(result.getShipId()).isEqualTo(SHIP_ID);
    }

    @Test
    public void generateEquipments_equipNewItem() {
        //GIVEN
        UpgradableItem upgradableItem = UpgradableItem.builder()
            .upgradableSlot(Optional.of(UpgradableSlot.CONNECTOR))
            .upgradeableItemId(Optional.empty())
            .build();
        given(upgradeableItemProvider.getUpgradableItem(any())).willReturn(upgradableItem);
        //WHEN
        ShipEquipments result = underTest.generateEquipments(Arrays.asList(gameShip));
        //THEN
        verify(newItemEquiper).equipNewItem(any(), eq(UpgradableSlot.CONNECTOR));
        assertThat(result.getShipId()).isEqualTo(SHIP_ID);
    }

    @Test
    public void generateEquipments_upgradeShip() {
        //GIVEN
        UpgradableItem upgradableItem = UpgradableItem.builder()
            .upgradableSlot(Optional.empty())
            .upgradeableItemId(Optional.empty())
            .build();
        given(upgradeableItemProvider.getUpgradableItem(any())).willReturn(upgradableItem);

        given(itemProviderFacade.getUpgradedVersionOf(SHIP_ID)).willReturn(Optional.of(UPGRADED_SHIP_ID));
        //WHEN
        ShipEquipments result = underTest.generateEquipments(Arrays.asList(gameShip));
        //THEN
        assertThat(result.getShipId()).isEqualTo(UPGRADED_SHIP_ID);
    }

    @Test
    public void generateEquipments_maxPoints() {
        //GIVEN
        UpgradableItem upgradableItem = UpgradableItem.builder()
            .upgradableSlot(Optional.empty())
            .upgradeableItemId(Optional.empty())
            .build();
        given(upgradeableItemProvider.getUpgradableItem(any())).willReturn(upgradableItem);

        given(itemProviderFacade.getUpgradedVersionOf(SHIP_ID)).willReturn(Optional.empty());
        //WHEN
        ShipEquipments result = underTest.generateEquipments(Arrays.asList(gameShip));
        //THEN
        assertThat(result.getShipId()).isEqualTo(SHIP_ID);
        verify(pointCalculator, times(1)).countPointsOfEquipments(any());
    }
}