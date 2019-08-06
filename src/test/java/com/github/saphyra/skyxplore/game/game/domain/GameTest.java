package com.github.saphyra.skyxplore.game.game.domain;

import com.github.saphyra.skyxplore.game.game.GameContext;
import com.github.saphyra.skyxplore.game.game.domain.ship.GameShip;
import com.github.saphyra.skyxplore.game.lobby.lobby.domain.GameMode;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class GameTest {
    private static final UUID GAME_ID = UUID.randomUUID();
    private static final String CHARCTER_ID = "character_id";

    @Mock
    private GameContext gameContext;

    private Game underTest;

    @Mock
    private Team team;

    @Mock
    private GameShip gameShip;

    @Before
    public void setUp() {
        underTest = Game.builder()
            .gameId(GAME_ID)
            .gameMode(GameMode.ARCADE)
            .gameContext(gameContext)
            .build();
    }

    @Test
    public void containsCharacter() {
        //GIVEN
        underTest.addTeams(Arrays.asList(team));
        given(team.containsCharacter(CHARCTER_ID)).willReturn(true);
        //WHEN
        boolean result = underTest.containsCharacter(CHARCTER_ID);
        //THEN
        assertThat(result).isTrue();
    }

    @Test
    public void addTeams() throws NoSuchFieldException, IllegalAccessException {
        //WHEN
        Game result = underTest.addTeams(Arrays.asList(team));
        //THEN
        assertThat(result).isEqualTo(underTest);
        Field f = result.getClass().getDeclaredField("teams"); //NoSuchFieldException
        f.setAccessible(true);
        //noinspection unchecked
        List<Team> teams = (List<Team>) f.get(result);
        assertThat(teams).containsExactly(team);
    }

    @Test
    public void addShips() {
        //WHEN
        underTest.addShips(Arrays.asList(gameShip));
        //THEN
        assertThat(underTest.getShips()).containsExactly(gameShip);
    }

    @Test
    public void addShip() {
        //WHEN
        underTest.addShip(gameShip);
        //THEN
        assertThat(underTest.getShips()).containsExactly(gameShip);
    }

    @Test
    public void getShips() {
        //GIVEN
        underTest.addShip(gameShip);
        //WHEN
        List<GameShip> result = underTest.getShips();
        //THEN
        assertThat(result).containsExactly(gameShip);
        result.add(gameShip);
        assertThat(underTest.getShips()).hasSize(1);
    }
}