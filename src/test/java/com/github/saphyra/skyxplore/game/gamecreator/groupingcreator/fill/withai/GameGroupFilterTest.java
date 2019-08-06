package com.github.saphyra.skyxplore.game.gamecreator.groupingcreator.fill.withai;

import com.github.saphyra.skyxplore.common.domain.FixedSizeConcurrentList;
import com.github.saphyra.skyxplore.game.gamecreator.domain.GameGroup;
import com.github.saphyra.skyxplore.game.gamecreator.domain.GameGroupCharacter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class GameGroupFilterTest {
    @InjectMocks
    private GameGroupFilter underTest;

    @Mock
    private GameGroup gameGroup1;

    @Mock
    private GameGroup gameGroup2;

    @Mock
    private GameGroup gameGroup3;

    @Mock
    private GameGroupCharacter gameGroupCharacter;

    @Test
    public void getAutoFillableGameGroups() {
        //GIVEN
        given(gameGroup1.getCharacters()).willReturn(new FixedSizeConcurrentList<>(1, Arrays.asList(gameGroupCharacter)));

        given(gameGroup2.getCharacters()).willReturn(new FixedSizeConcurrentList<>(2, Arrays.asList(gameGroupCharacter)));
        given(gameGroup2.isAutoFill()).willReturn(false);

        given(gameGroup3.getCharacters()).willReturn(new FixedSizeConcurrentList<>(2, Arrays.asList(gameGroupCharacter)));
        given(gameGroup3.isAutoFill()).willReturn(true);
        //WHEN
        List<GameGroup> result = underTest.getAutoFillableGameGroups(Arrays.asList(gameGroup1, gameGroup2, gameGroup3));
        //THEN
        assertThat(result).containsExactly(gameGroup3);
    }
}