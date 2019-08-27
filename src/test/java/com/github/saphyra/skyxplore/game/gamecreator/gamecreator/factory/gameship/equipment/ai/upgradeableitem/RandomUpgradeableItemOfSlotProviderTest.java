package com.github.saphyra.skyxplore.game.gamecreator.gamecreator.factory.gameship.equipment.ai.upgradeableitem;

import com.github.saphyra.skyxplore.data.gamedata.GameDataFacade;
import com.github.saphyra.util.Random;
import org.junit.Before;
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
public class RandomUpgradeableItemOfSlotProviderTest {
    private static final String ITEM_ID = "item_id";

    @Mock
    private GameDataFacade gameDataFacade;

    @Mock
    private Random random;

    @InjectMocks
    private RandomUpgradeableItemOfSlotProvider underTest;

    @Before
    public void setUp() {
        given(random.randInt(-1, 1)).willReturn(0);
    }

    @Test
    public void getRandomUpgradableItemOfSlot_found() {
        //GIVEN
        given(gameDataFacade.isUpgradable(ITEM_ID)).willReturn(true);
        //WHEN
        Optional<String> result = underTest.getRandomUpgradableItemOfSlot(Arrays.asList(ITEM_ID, ITEM_ID));
        //THEN
        assertThat(result).contains(ITEM_ID);
    }

    @Test
    public void getRandomUpgradableItemOfSlot_notFound() {
        //GIVEN
        given(gameDataFacade.isUpgradable(ITEM_ID)).willReturn(false);
        //WHEN
        Optional<String> result = underTest.getRandomUpgradableItemOfSlot(Arrays.asList(ITEM_ID, ITEM_ID));
        //THEN
        assertThat(result).isEmpty();
    }
}