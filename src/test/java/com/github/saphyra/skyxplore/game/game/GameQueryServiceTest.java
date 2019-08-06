package com.github.saphyra.skyxplore.game.game;

import com.github.saphyra.skyxplore.game.game.domain.Game;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class GameQueryServiceTest {
    private static final String CHARACTER_ID = "character_id";
    @Mock
    private GameStorage gameStorage;

    @InjectMocks
    private GameQueryService underTest;

    @Mock
    private Game game;

    @Test
    public void findByCharacterId_notFound() {
        //GIVEN
        given(game.containsCharacter(CHARACTER_ID)).willReturn(false);
        given(gameStorage.values()).willReturn(Arrays.asList(game));
        //WHEN
        Optional<Game> result = underTest.findByCharacterId(CHARACTER_ID);
        //THEN
        assertThat(result).isEmpty();
    }

    @Test
    public void findByCharacterId_found() {
        //GIVEN
        given(game.containsCharacter(CHARACTER_ID)).willReturn(true);
        given(gameStorage.values()).willReturn(Arrays.asList(game));
        //WHEN
        Optional<Game> result = underTest.findByCharacterId(CHARACTER_ID);
        //THEN
        assertThat(result).contains(game);
    }
}