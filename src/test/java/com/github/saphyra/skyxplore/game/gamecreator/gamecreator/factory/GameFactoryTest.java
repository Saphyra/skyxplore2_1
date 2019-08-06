package com.github.saphyra.skyxplore.game.gamecreator.gamecreator.factory;

import static com.github.saphyra.testing.ReflectionUtil.getField;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;

import java.util.Arrays;
import java.util.Collections;
import java.util.UUID;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.github.saphyra.skyxplore.common.domain.FixedSizeConcurrentList;
import com.github.saphyra.skyxplore.game.game.GameContext;
import com.github.saphyra.skyxplore.game.game.domain.Game;
import com.github.saphyra.skyxplore.game.game.domain.Team;
import com.github.saphyra.skyxplore.game.game.domain.ship.GameShip;
import com.github.saphyra.skyxplore.game.gamecreator.domain.GameGroup;
import com.github.saphyra.skyxplore.game.gamecreator.domain.GameGroupCharacter;
import com.github.saphyra.skyxplore.game.gamecreator.domain.GameGrouping;
import com.github.saphyra.skyxplore.game.gamecreator.gamecreator.factory.gameship.GameShipFactory;
import com.github.saphyra.skyxplore.game.lobby.lobby.domain.GameMode;
import com.github.saphyra.util.IdGenerator;

@RunWith(MockitoJUnitRunner.class)
public class GameFactoryTest {
    private static final UUID GAME_ID = UUID.randomUUID();
    private static final Object GAME_DATA = "game_data";

    @Mock
    private GameContext gameContext;

    @Mock
    private GameShipFactory gameShipFactory;

    @Mock
    private IdGenerator idGenerator;

    @Mock
    private TeamFactory teamFactory;

    @InjectMocks
    private GameFactory underTest;

    @Mock
    private GameGrouping gameGrouping;

    @Mock
    private Team team;

    @Mock
    private GameGroup gameGroup;

    @Mock
    private GameGroupCharacter gameGroupCharacter;

    @Mock
    private GameGroupCharacter aiGameGroupCharacter;

    @Mock
    private GameShip gameShip;

    @Mock
    private GameShip aiGameShip;

    @Test
    public void create() throws NoSuchFieldException, IllegalAccessException {
        //GIVEN
        given(teamFactory.create(gameGrouping)).willReturn(Arrays.asList(team));
        given(idGenerator.randomUUID()).willReturn(GAME_ID);
        given(gameGrouping.getGameMode()).willReturn(GameMode.TEAMFIGHT);
        given(gameGrouping.getData()).willReturn(GAME_DATA);

        given(gameGrouping.getGameGroups()).willReturn(Arrays.asList(gameGroup));
        given(gameGroup.getCharacters()).willReturn(new FixedSizeConcurrentList<>(2, Arrays.asList(gameGroupCharacter, aiGameGroupCharacter)));
        given(gameGroupCharacter.isAi()).willReturn(false);
        given(aiGameGroupCharacter.isAi()).willReturn(true);

        given(gameShipFactory.create(eq(gameGroupCharacter), any())).willReturn(gameShip);
        given(gameShipFactory.create(eq(aiGameGroupCharacter), any())).willReturn(aiGameShip);
        //WHEN
        Game result = underTest.create(gameGrouping);
        //THEN
        assertThat(result.getGameId()).isEqualTo(GAME_ID);
        assertThat(result.getGameMode()).isEqualTo(GameMode.TEAMFIGHT);
        assertThat(result.getData()).isEqualTo(GAME_DATA);

        assertThat(getField(result, "gameContext")).isEqualTo(gameContext);
        assertThat(getField(result, "teams")).isEqualTo(Arrays.asList(team));
        assertThat(getField(result, "ships")).isEqualTo(Arrays.asList(gameShip, aiGameShip));
        assertThat(getField(result, "messages")).isEqualTo(Collections.emptyList());
    }


}