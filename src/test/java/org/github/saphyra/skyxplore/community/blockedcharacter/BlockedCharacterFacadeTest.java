package org.github.saphyra.skyxplore.community.blockedcharacter;

import org.github.saphyra.skyxplore.character.CharacterQueryService;
import org.github.saphyra.skyxplore.character.domain.SkyXpCharacter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import skyxplore.service.community.BlockCharacterService;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BlockedCharacterFacadeTest {
    private static final String BLOCKED_CHARACTER_ID = "blocked_character_id";
    private static final String CHARACTER_ID = "character_id";
    private static final String CHARACTER_NAME = "character_name";
    @Mock
    private BlockCharacterService blockCharacterService;

    @Mock
    private CharacterQueryService characterQueryService;

    @InjectMocks
    private BlockedCharacterFacade underTest;

    @Test
    public void testAllowBlockedCharacterShouldCallService() {
        //WHEN
        underTest.allowBlockedCharacter(BLOCKED_CHARACTER_ID, CHARACTER_ID);
        //THEN
        verify(blockCharacterService).allowBlockedCharacter(BLOCKED_CHARACTER_ID, CHARACTER_ID);
    }

    @Test
    public void testBlockCharacterShouldCallService() {
        //WHEN
        underTest.blockCharacter(BLOCKED_CHARACTER_ID, CHARACTER_ID);
        //THEN
        verify(blockCharacterService).blockCharacter(BLOCKED_CHARACTER_ID, CHARACTER_ID);
    }

    @Test
    public void testGetBlockedCharactersShouldCallServiceAndReturn() {
        //GIVEN
        SkyXpCharacter character = SkyXpCharacter.builder().build();
        when(characterQueryService.getBlockedCharacters(CHARACTER_ID)).thenReturn(Arrays.asList(character));
        //WHEN
        List<SkyXpCharacter> result = underTest.getBlockedCharacters(CHARACTER_ID);
        //THEN
        verify(characterQueryService).getBlockedCharacters(CHARACTER_ID);
        assertThat(result).containsOnly(character);
    }

    @Test
    public void testGetCharactersCanBeBlockedShouldCallServiceAndReturn() {
        //GIVEN
        SkyXpCharacter character = SkyXpCharacter.builder().build();
        when(characterQueryService.getCharactersCanBeBlocked(CHARACTER_NAME, CHARACTER_ID)).thenReturn(Arrays.asList(character));
        //WHEN
        List<SkyXpCharacter> result = underTest.getCharactersCanBeBlocked(CHARACTER_NAME, CHARACTER_ID);
        //THEN
        verify(characterQueryService).getCharactersCanBeBlocked(CHARACTER_NAME, CHARACTER_ID);
        assertThat(result).containsOnly(character);
    }
}