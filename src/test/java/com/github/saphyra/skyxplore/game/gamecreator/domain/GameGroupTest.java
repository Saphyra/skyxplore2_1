package com.github.saphyra.skyxplore.game.gamecreator.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.UUID;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class GameGroupTest {
    private static final UUID GAME_GROUP_ID = UUID.randomUUID();
    private static final int MAX_GROUP_SIZE = 2;
    private static final boolean AUTO_FILL = true;

    private GameGroup underTest;

    @Mock
    private GameGroupCharacter gameGroupCharacter;

    @Before
    public void setUp() {
        underTest = GameGroup.builder()
            .gameGroupId(GAME_GROUP_ID)
            .maxGroupSize(MAX_GROUP_SIZE)
            .autoFill(AUTO_FILL)
            .build();
    }

    @Test
    public void addCharacter() {
        //WHEN
        GameGroup result = underTest.addCharacter(gameGroupCharacter);
        //THEN
        assertThat(result).isEqualTo(underTest);
        assertThat(underTest.getCharacters()).containsExactly(gameGroupCharacter);
    }

    @Test
    public void getCharacters() {
        //GIVEN
        underTest.addCharacter(gameGroupCharacter);
        //WHEN
        List<GameGroupCharacter> result = underTest.getCharacters();
        //THEN
        assertThat(result).containsExactly(gameGroupCharacter);
        result.add(gameGroupCharacter);
        assertThat(underTest.getCharacters()).containsExactly(gameGroupCharacter);
    }
}