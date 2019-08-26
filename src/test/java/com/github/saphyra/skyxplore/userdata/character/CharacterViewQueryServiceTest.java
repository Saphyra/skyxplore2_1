package com.github.saphyra.skyxplore.userdata.character;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.BDDMockito.given;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.github.saphyra.skyxplore.userdata.character.domain.SkyXpCharacter;
import com.github.saphyra.skyxplore.common.domain.character.CharacterView;
import com.github.saphyra.skyxplore.common.domain.character.CharacterViewConverter;

@RunWith(MockitoJUnitRunner.class)
public class CharacterViewQueryServiceTest {
    private static final String CHARACTER_ID = "character_id";
    private static final String CHARACTER_NAME = "character_name";
    private static final String USER_ID = "user_id";
    @Mock
    private CharacterQueryService characterQueryService;

    @Mock
    private CharacterViewConverter characterViewConverter;

    @Mock
    private SkyXpCharacter character;

    @Mock
    private CharacterView characterView;

    @InjectMocks
    private CharacterViewQueryService underTest;

    @Test
    public void findByCharacterId() {
        //GIVEN
        given(characterQueryService.findByCharacterIdValidated(CHARACTER_ID)).willReturn(character);

        given(characterViewConverter.convertDomain(character)).willReturn(characterView);
        //WHEN
        CharacterView result = underTest.findByCharacterId(CHARACTER_ID);
        //THEN
        assertThat(result).isEqualTo(characterView);
    }

    @Test
    public void getActiveCharactersByName() {
        //GIVEN
        List<SkyXpCharacter> characters = Arrays.asList(character);
        given(characterQueryService.getActiveCharactersByName(CHARACTER_ID, CHARACTER_NAME)).willReturn(characters);

        List<CharacterView> characterViews = Arrays.asList(characterView);
        given(characterViewConverter.convertDomain(characters)).willReturn(characterViews);
        //WHEN
        List<CharacterView> result = underTest.getActiveCharactersByName(CHARACTER_ID, CHARACTER_NAME);
        //THEN
        assertThat(result).isEqualTo(characterViews);
    }

    @Test
    public void getCharactersByUserId() {
        //GIVEN
        List<SkyXpCharacter> characters = Arrays.asList(character);
        given(characterQueryService.getCharactersByUserId(USER_ID)).willReturn(characters);

        List<CharacterView> characterViews = Arrays.asList(characterView);
        given(characterViewConverter.convertDomain(characters)).willReturn(characterViews);
        //WHEN
        List<CharacterView> result = underTest.getCharactersByUserId(USER_ID);
        //THEN
        assertThat(result).isEqualTo(characterViews);
    }
}