package com.github.saphyra.skyxplore.game.gamecreator.gamecreator.factory;

import static com.github.saphyra.testing.ReflectionUtil.getField;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.github.saphyra.skyxplore.common.domain.FixedSizeConcurrentList;
import com.github.saphyra.skyxplore.game.game.domain.GameCharacter;
import com.github.saphyra.skyxplore.game.game.domain.Team;
import com.github.saphyra.skyxplore.game.game.domain.TeamColor;
import com.github.saphyra.skyxplore.game.gamecreator.domain.GameGroup;
import com.github.saphyra.skyxplore.game.gamecreator.domain.GameGroupCharacter;
import com.github.saphyra.skyxplore.game.gamecreator.domain.GameGrouping;
import com.github.saphyra.util.IdGenerator;

@RunWith(MockitoJUnitRunner.class)
public class TeamFactoryTest {
    private static final UUID TEAM_ID = UUID.randomUUID();
    @Mock
    private GameCharacterFactory gameCharacterFactory;

    @Mock
    private IdGenerator idGenerator;

    @Mock
    private TeamColorPicker teamColorPicker;

    @InjectMocks
    private TeamFactory underTest;

    @Mock
    private GameGrouping gameGrouping;

    @Mock
    private GameGroup gameGroup;

    @Mock
    private GameGroupCharacter gameGroupCharacter;

    @Mock
    private GameCharacter gameCharacter;

    @Test
    public void create() throws NoSuchFieldException, IllegalAccessException {
        //GIVEN
        given(gameGrouping.getGameGroups()).willReturn(Arrays.asList(gameGroup));
        given(teamColorPicker.getRandomColor(Collections.emptyList())).willReturn(TeamColor.BLUE);
        given(gameGroup.getCharacters()).willReturn(new FixedSizeConcurrentList<>(1, Arrays.asList(gameGroupCharacter)));
        given(gameCharacterFactory.create(Arrays.asList(gameGroupCharacter))).willReturn(Arrays.asList(gameCharacter));
        given(idGenerator.randomUUID()).willReturn(TEAM_ID);
        //WHEN
        List<Team> result = underTest.create(gameGrouping);
        //THEN
        assertThat(result).hasSize(1);
        assertThat(getField(result.get(0), "teamId")).isEqualTo(TEAM_ID);
        assertThat(getField(result.get(0), "teamColor")).isEqualTo(TeamColor.BLUE);
        assertThat(getField(result.get(0), "characters")).isEqualTo(Arrays.asList(gameCharacter));
    }
}