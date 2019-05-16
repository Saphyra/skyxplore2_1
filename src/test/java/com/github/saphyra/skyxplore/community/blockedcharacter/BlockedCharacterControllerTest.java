package com.github.saphyra.skyxplore.community.blockedcharacter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.github.saphyra.skyxplore.character.CharacterQueryService;
import com.github.saphyra.skyxplore.character.domain.SkyXpCharacter;
import com.github.saphyra.skyxplore.common.OneStringParamRequest;
import com.github.saphyra.skyxplore.common.domain.character.CharacterView;
import com.github.saphyra.skyxplore.common.domain.character.CharacterViewConverter;

@RunWith(MockitoJUnitRunner.class)
public class BlockedCharacterControllerTest {
    private static final String BLOCKED_CHARACTER_ID = "blocked_character_id";
    private static final String CHARACTER_ID = "character_id";
    private static final String CHARACTER_NAME = "character_name";

    @Mock
    private BlockedCharacterQueryService blockedCharacterQueryService;

    @Mock
    private BlockCharacterService blockCharacterService;

    @Mock
    private CharacterQueryService characterQueryService;

    @Mock
    private CharacterViewConverter characterViewConverter;

    @InjectMocks
    private BlockedCharacterController underTest;

    @Mock
    private SkyXpCharacter character;

    @Mock
    private CharacterView characterView;

    @Test
    public void testAllowBlockedCharacterShouldCallFacade() {
        //WHEN
        underTest.allowBlockedCharacter(new OneStringParamRequest(BLOCKED_CHARACTER_ID), CHARACTER_ID);
        //THEN
        verify(blockCharacterService).allowBlockedCharacter(BLOCKED_CHARACTER_ID, CHARACTER_ID);
    }

    @Test
    public void testBlockCharacterShouldCallFacade() {
        //WHEN
        underTest.blockCharacter(new OneStringParamRequest(BLOCKED_CHARACTER_ID), CHARACTER_ID);
        //THEN
        verify(blockCharacterService).blockCharacter(BLOCKED_CHARACTER_ID, CHARACTER_ID);
    }

    @Test
    public void testGetBlockedCharactersShouldCallFacadeAndReturnView() {
        //GIVEN
        List<SkyXpCharacter> characterList = Arrays.asList(character);
        when(blockedCharacterQueryService.getBlockedCharacters(CHARACTER_ID)).thenReturn(characterList);

        List<CharacterView> viewList = Arrays.asList(characterView);
        when(characterViewConverter.convertDomain(characterList)).thenReturn(viewList);
        //WHEN
        List<CharacterView> result = underTest.getBlockedCharacters(CHARACTER_ID);
        //THEN
        verify(characterViewConverter).convertDomain(characterList);
        assertThat(result).isEqualTo(viewList);
    }

    @Test
    public void testGetCharactersCanBeBlockedShouldCallFacadeAndReturnView() {
        //GIVEN
        List<SkyXpCharacter> characterList = Arrays.asList(character);
        when(characterQueryService.getCharactersCanBeBlocked(CHARACTER_NAME, CHARACTER_ID)).thenReturn(characterList);

        List<CharacterView> viewList = Arrays.asList(characterView);
        when(characterViewConverter.convertDomain(characterList)).thenReturn(viewList);
        //WHEN
        List<CharacterView> result = underTest.getCharactersCanBeBlocked(new OneStringParamRequest(CHARACTER_NAME), CHARACTER_ID);
        //THEN
        verify(characterViewConverter).convertDomain(characterList);
        assertThat(result).isEqualTo(viewList);
    }
}