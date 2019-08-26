package com.github.saphyra.skyxplore.game.gamecreator.gamecreator.factory.ainame;

import com.github.saphyra.skyxplore.game.gamecreator.domain.GameGroupCharacter;
import com.github.saphyra.skyxplore.userdata.character.CharacterQueryService;
import com.github.saphyra.skyxplore.userdata.character.domain.SkyXpCharacter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class CharacterNameResolverTest {
    private static final String CHARACTER_NAME = "character_name";
    private static final String CHARACTER_ID = "character_id";

    @Mock
    private AiNameGenerator aiNameGenerator;

    @Mock
    private CharacterQueryService characterQueryService;

    @InjectMocks
    private CharacterNameResolver underTest;

    @Mock
    private GameGroupCharacter gameGroupCharacter;

    @Mock
    private SkyXpCharacter character;

    @Test
    public void getCharacterName_ai() {
        //GIVEN
        given(gameGroupCharacter.isAi()).willReturn(true);
        given(aiNameGenerator.generateCharacterName()).willReturn(CHARACTER_NAME);
        //WHEN
        String result = underTest.getCharacterName(gameGroupCharacter);
        //THEN
        assertThat(result).isEqualTo(CHARACTER_NAME);
    }

    @Test
    public void getCharacterName_normalCharacter() {
        //GIVEN
        given(gameGroupCharacter.isAi()).willReturn(false);
        given(character.getCharacterName()).willReturn(CHARACTER_NAME);
        given(gameGroupCharacter.getCharacterId()).willReturn(CHARACTER_ID);
        given(characterQueryService.findByCharacterIdValidated(CHARACTER_ID)).willReturn(character);
        //WHEN
        String result = underTest.getCharacterName(gameGroupCharacter);
        //THEN
        assertThat(result).isEqualTo(CHARACTER_NAME);
    }
}