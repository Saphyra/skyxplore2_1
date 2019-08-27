package com.github.saphyra.skyxplore.game.gamecreator.gamecreator.factory.gameship;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.github.saphyra.skyxplore.data.gamedata.entity.Armor;
import com.github.saphyra.skyxplore.data.gamedata.subservice.ArmorService;
import com.github.saphyra.skyxplore.game.game.domain.ship.HullDetails;

@RunWith(MockitoJUnitRunner.class)
public class HullDetailsFactoryTest {
    private static final String ITEM_ID = "item_id";
    private static final Integer CAPACITY = 235;

    @Mock
    private ArmorService armorService;

    @InjectMocks
    private HullDetailsFactory underTest;

    @Mock
    private Armor armor;

    @Test
    public void create() {
        //GIVEN
        given(armorService.get(ITEM_ID)).willReturn(armor);
        given(armor.getCapacity()).willReturn(CAPACITY);
        //WHEN
        List<HullDetails> result = underTest.create(Arrays.asList(ITEM_ID));
        //THEN
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getItemId()).isEqualTo(ITEM_ID);
        assertThat(result.get(0).getMaxHull()).isEqualTo(CAPACITY);
        assertThat(result.get(0).getActualHull()).isEqualTo(CAPACITY);
    }
}