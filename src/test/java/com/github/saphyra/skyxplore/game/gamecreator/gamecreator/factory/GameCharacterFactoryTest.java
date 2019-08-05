package com.github.saphyra.skyxplore.game.gamecreator.gamecreator.factory;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.github.saphyra.skyxplore.game.game.domain.GameCharacter;
import com.github.saphyra.skyxplore.game.gamecreator.domain.GameGroupCharacter;
import com.github.saphyra.skyxplore.game.gamecreator.gamecreator.factory.ainame.CharacterNameResolver;

@RunWith(MockitoJUnitRunner.class)
public class GameCharacterFactoryTest {
    private static final String CHARACTER_ID = "character_id";
    private static final String CHARACTER_NAME = "character_name";

    @Mock
    private CharacterNameResolver characterNameResolver;

    @InjectMocks
    private GameCharacterFactory underTest;

    @Mock
    private GameGroupCharacter gameGroupCharacter;

    @Test
    public void create() {
        //GIVEN
        given(gameGroupCharacter.getCharacterId()).willReturn(CHARACTER_ID);
        given(characterNameResolver.getCharacterName(gameGroupCharacter)).willReturn(CHARACTER_NAME);
        given(gameGroupCharacter.isAi()).willReturn(true);
        //WHEN
        List<GameCharacter> result = underTest.create(Arrays.asList(gameGroupCharacter));
        //THEN
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getCharacterId()).isEqualTo(CHARACTER_ID);
        assertThat(result.get(0).getCharacterName()).isEqualTo(CHARACTER_NAME);
        assertThat(result.get(0).isOriginallyAi()).isTrue();
        assertThat(result.get(0).isAi()).isTrue();
    }
}