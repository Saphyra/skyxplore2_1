package com.github.saphyra.skyxplore.game.game.domain;

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
public class TeamTest {
    private static final UUID TEAM_ID = UUID.randomUUID();
    private static final String CHARACTER_ID = "character_id";

    private Team underTest;

    @Mock
    private GameCharacter gameCharacter;

    @Before
    public void setUp() {
        underTest = Team.builder()
            .teamId(TEAM_ID)
            .teamColor(TeamColor.BLUE)
            .build();
    }

    @Test
    public void addCharacters() throws NoSuchFieldException, IllegalAccessException {
        //WHEN
        Team result = underTest.addCharacters(Arrays.asList(gameCharacter));
        //THEN
        assertThat(result).isEqualTo(underTest);
        Field f = result.getClass().getDeclaredField("characters"); //NoSuchFieldException
        f.setAccessible(true);
        //noinspection unchecked
        List<GameCharacter> teams = (List<GameCharacter>) f.get(result);
        assertThat(teams).containsExactly(gameCharacter);
    }

    @Test
    public void containsCharacter() {
        //GIVEN
        underTest.addCharacters(Arrays.asList(gameCharacter));
        given(gameCharacter.getCharacterId()).willReturn(CHARACTER_ID);
        //WHEN
        boolean result = underTest.containsCharacter(CHARACTER_ID);
        //THEN
        assertThat(result).isTrue();
    }
}