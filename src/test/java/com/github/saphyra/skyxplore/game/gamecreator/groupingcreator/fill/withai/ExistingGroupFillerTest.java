package com.github.saphyra.skyxplore.game.gamecreator.groupingcreator.fill.withai;

import com.github.saphyra.skyxplore.common.domain.FixedSizeConcurrentList;
import com.github.saphyra.skyxplore.game.gamecreator.domain.GameGroup;
import com.github.saphyra.skyxplore.game.gamecreator.domain.GameGroupCharacter;
import com.github.saphyra.skyxplore.game.gamecreator.domain.GameGrouping;
import com.github.saphyra.skyxplore.game.gamecreator.groupingcreator.GameGroupCharacterFactory;
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
public class ExistingGroupFillerTest {
    @Mock
    private GameGroupCharacterFactory gameGroupCharacterFactory;

    @Mock
    private GameGroupFilter gameGroupFilter;

    @InjectMocks
    private ExistingGroupFiller underTest;

    @Mock
    private GameGrouping gameGrouping;

    @Mock
    private GameGroup gameGroup;

    @Mock
    private GameGroupCharacter gameGroupCharacter;

    @Test
    public void fillEmptyPlacesInExistingGroups() {
        //GIVEN
        given(gameGrouping.getMinimumGameMembersAmount()).willReturn(3);
        given(gameGrouping.getMembersAmount()).willReturn(1);
        given(gameGrouping.getGameGroups()).willReturn(Arrays.asList(gameGroup));
        given(gameGroupFilter.getAutoFillableGameGroups(Arrays.asList(gameGroup))).willReturn(Arrays.asList(gameGroup));
        given(gameGroup.getCharacters()).willReturn(new FixedSizeConcurrentList<>(1));
        given(gameGroupCharacterFactory.createAi()).willReturn(gameGroupCharacter);
        //WHEN
        int result = underTest.fillEmptyPlacesInExistingGroups(gameGrouping);
        //THEN
        verify(gameGroup).addCharacter(gameGroupCharacter);
        assertThat(result).isEqualTo(1);
    }
}