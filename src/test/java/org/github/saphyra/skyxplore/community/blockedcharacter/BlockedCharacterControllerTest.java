package org.github.saphyra.skyxplore.community.blockedcharacter;

import org.github.saphyra.skyxplore.character.domain.SkyXpCharacter;
import org.github.saphyra.skyxplore.common.OneStringParamRequest;
import org.github.saphyra.skyxplore.common.domain.character.CharacterView;
import org.github.saphyra.skyxplore.common.domain.character.CharacterViewConverter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BlockedCharacterControllerTest {
    private static final String BLOCKED_CHARACTER_ID = "blocked_character_id";
    private static final String CHARACTER_ID = "character_id";
    private static final String CHARACTER_NAME = "character_name";

    @Mock
    private BlockedCharacterFacade blockedCharacterFacade;

    @Mock
    private CharacterViewConverter characterViewConverter;

    @InjectMocks
    private BlockedCharacterController underTest;

    @Test
    public void testAllowBlockedCharacterShouldCallFacade() {
        //WHEN
        underTest.allowBlockedCharacter(new OneStringParamRequest(BLOCKED_CHARACTER_ID), CHARACTER_ID);
        //THEN
        verify(blockedCharacterFacade).allowBlockedCharacter(BLOCKED_CHARACTER_ID, CHARACTER_ID);
    }

    @Test
    public void testBlockCharacterShouldCallFacade() {
        //WHEN
        underTest.blockCharacter(new OneStringParamRequest(BLOCKED_CHARACTER_ID), CHARACTER_ID);
        //THEN
        verify(blockedCharacterFacade).blockCharacter(BLOCKED_CHARACTER_ID, CHARACTER_ID);
    }

    @Test
    public void testGetBlockedCharactersShouldCallFacadeAndReturnView() {
        //GIVEN
        SkyXpCharacter blockedCharacter = SkyXpCharacter.builder().build();
        List<SkyXpCharacter> characterList = Arrays.asList(blockedCharacter);
        when(blockedCharacterFacade.getBlockedCharacters(CHARACTER_ID)).thenReturn(characterList);

        CharacterView characterView = CharacterView.builder().build();
        List<CharacterView> viewList = Arrays.asList(characterView);
        when(characterViewConverter.convertDomain(characterList)).thenReturn(viewList);
        //WHEN
        List<CharacterView> result = underTest.getBlockedCharacters(CHARACTER_ID);
        //THEN
        verify(blockedCharacterFacade).getBlockedCharacters(CHARACTER_ID);
        verify(characterViewConverter).convertDomain(characterList);
        assertThat(result).isEqualTo(viewList);
    }

    @Test
    public void testGetCharactersCanBeBlockedShouldCallFacadeAndReturnView() {
        //GIVEN
        SkyXpCharacter character = SkyXpCharacter.builder().build();
        List<SkyXpCharacter> characterList = Arrays.asList(character);
        when(blockedCharacterFacade.getCharactersCanBeBlocked(CHARACTER_NAME, CHARACTER_ID)).thenReturn(characterList);

        CharacterView characterView = CharacterView.builder().build();
        List<CharacterView> viewList = Arrays.asList(characterView);
        when(characterViewConverter.convertDomain(characterList)).thenReturn(viewList);
        //WHEN
        List<CharacterView> result = underTest.getCharactersCanBeBlocked(new OneStringParamRequest(CHARACTER_NAME), CHARACTER_ID);
        //THEN
        verify(blockedCharacterFacade).getCharactersCanBeBlocked(CHARACTER_NAME, CHARACTER_ID);
        verify(characterViewConverter).convertDomain(characterList);
        assertThat(result).isEqualTo(viewList);
    }
}