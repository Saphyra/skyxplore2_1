package com.github.saphyra.skyxplore.game.gamecreator.groupingcreator.fill.withai;

import com.github.saphyra.skyxplore.game.gamecreator.domain.GameGroup;
import com.github.saphyra.skyxplore.game.gamecreator.domain.GameGroupCharacter;
import com.github.saphyra.skyxplore.game.gamecreator.domain.GameGroupSizeRange;
import com.github.saphyra.skyxplore.game.gamecreator.domain.GameGrouping;
import com.github.saphyra.skyxplore.game.gamecreator.groupingcreator.GameGroupCharacterFactory;
import com.github.saphyra.skyxplore.game.gamecreator.groupingcreator.GameGroupFactory;
import com.github.saphyra.util.Random;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class FillWithAiGroupsServiceTest {
    private static final Integer MIN_GROUP_SIZE = 3;
    private static final Integer MAX_GROUP_SIZE = 5;

    @Mock
    private GameGroupCharacterFactory gameGroupCharacterFactory;

    @Mock
    private GameGroupFactory gameGroupFactory;

    @Mock
    private Random random;

    @InjectMocks
    private FillWithAiGroupsService underTest;

    @Captor
    private ArgumentCaptor<List<GameGroupCharacter>> argumentCaptor;

    @Mock
    private GameGrouping gameGrouping;

    @Mock
    private GameGroupCharacter gameGroupCharacter;

    @Mock
    private GameGroupSizeRange gameGroupSizeRange;

    @Mock
    private GameGroup gameGroup;

    @Test
    public void fillGameWithAiGroups() {
        //GIVEN
        given(gameGroupCharacterFactory.createAi()).willReturn(gameGroupCharacter);
        given(gameGrouping.getGameGroupSizeRange()).willReturn(gameGroupSizeRange);
        given(gameGroupSizeRange.getMinGroupSize()).willReturn(MIN_GROUP_SIZE);
        given(gameGroupSizeRange.getMaxGroupSize()).willReturn(MAX_GROUP_SIZE);
        given(random.randInt(MIN_GROUP_SIZE, MAX_GROUP_SIZE)).willReturn(MIN_GROUP_SIZE);
        given(gameGroupFactory.createGroups(any(), eq(false), eq(MIN_GROUP_SIZE))).willReturn(Arrays.asList(gameGroup));
        //WHEN
        underTest.fillGameWithAiGroups(gameGrouping, 1);
        //THEN
        verify(gameGroupFactory).createGroups(argumentCaptor.capture(), eq(false), eq(MIN_GROUP_SIZE));
        assertThat(argumentCaptor.getValue()).containsExactly(gameGroupCharacter);
        verify(gameGrouping).addGroup(gameGroup);
    }
}