package com.github.saphyra.skyxplore.character;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.BDDMockito.given;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.github.saphyra.skyxplore.character.domain.SkyXpCharacter;
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

    @InjectMocks
    private CharacterViewQueryService underTest;

    @Test
    public void findByCharacterId() {
        //GIVEN
        SkyXpCharacter character = SkyXpCharacter.builder().build();
        given(characterQueryService.findByCharacterId(CHARACTER_ID)).willReturn(character);

        CharacterView characterView = CharacterView.builder().build();
        given(characterViewConverter.convertDomain(character)).willReturn(characterView);
        //WHEN
        CharacterView result = underTest.findByCharacterId(CHARACTER_ID);
        //THEN
        assertThat(result).isEqualTo(characterView);
    }

    @Test
    public void getActiveCharactersByName() {
        //GIVEN
        List<SkyXpCharacter> character = Arrays.asList(SkyXpCharacter.builder().build());
        given(characterQueryService.getActiveCharactersByName(CHARACTER_ID, CHARACTER_NAME)).willReturn(character);

        List<CharacterView> characterView = Arrays.asList(CharacterView.builder().build());
        given(characterViewConverter.convertDomain(character)).willReturn(characterView);
        //WHEN
        List<CharacterView> result = underTest.getActiveCharactersByName(CHARACTER_ID, CHARACTER_NAME);
        //THEN
        assertThat(result).isEqualTo(characterView);
    }

    @Test
    public void getCharactersByUserId() {
        //GIVEN
        List<SkyXpCharacter> character = Arrays.asList(SkyXpCharacter.builder().build());
        given(characterQueryService.getCharactersByUserId(USER_ID)).willReturn(character);

        List<CharacterView> characterView = Arrays.asList(CharacterView.builder().build());
        given(characterViewConverter.convertDomain(character)).willReturn(characterView);
        //WHEN
        List<CharacterView> result = underTest.getCharactersByUserId(USER_ID);
        //THEN
        assertThat(result).isEqualTo(characterView);
    }
}