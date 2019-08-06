package com.github.saphyra.skyxplore.game.gamecreator.groupingcreator;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.github.saphyra.skyxplore.game.gamecreator.domain.GameGroupCharacter;
import com.github.saphyra.util.IdGenerator;

@RunWith(MockitoJUnitRunner.class)
public class GameGroupCharacterFactoryTest {
    private static final String CHARACTER_ID = "character_id";

    @Mock
    private IdGenerator idGenerator;

    @InjectMocks
    private GameGroupCharacterFactory underTest;

    @Test
    public void createGameGroupCharacter() {
        //WHEN
        GameGroupCharacter result = underTest.createGameGroupCharacter(CHARACTER_ID, true);
        //THEN
        assertThat(result.getCharacterId()).isEqualTo(CHARACTER_ID);
        assertThat(result.isAi()).isTrue();
    }

    @Test
    public void createAi() {
        //GIVEN
        given(idGenerator.generateRandomId()).willReturn(CHARACTER_ID);
        //WHEN
        GameGroupCharacter result = underTest.createAi();
        //THEN
        assertThat(result.getCharacterId()).isEqualTo(CHARACTER_ID);
        assertThat(result.isAi()).isTrue();
    }
}