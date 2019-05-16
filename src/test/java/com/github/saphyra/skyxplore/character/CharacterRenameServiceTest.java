package com.github.saphyra.skyxplore.character;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.github.saphyra.skyxplore.character.cache.CharacterNameExistsCache;
import com.github.saphyra.skyxplore.character.domain.RenameCharacterRequest;
import com.github.saphyra.skyxplore.character.domain.SkyXpCharacter;
import com.github.saphyra.skyxplore.character.repository.CharacterDao;
import com.github.saphyra.skyxplore.common.exception.CharacterNameAlreadyExistsException;

@RunWith(MockitoJUnitRunner.class)
public class CharacterRenameServiceTest {
    private static final String NEW_CHARACTER_NAME = "new_character_name";
    private static final String CHARACTER_ID = "character_id";
    private static final String USER_ID = "user_id";

    @Mock
    private CharacterDao characterDao;

    @Mock
    private CharacterNameExistsCache characterNameExistsCache;

    @Mock
    private CharacterQueryService characterQueryService;

    @Mock
    private SkyXpCharacter character;

    @InjectMocks
    private CharacterRenameService underTest;

    @Test(expected = CharacterNameAlreadyExistsException.class)
    public void testRenameCharacterShouldThrowExceptionWhenCharacterNameExists() {
        //GIVEN
        RenameCharacterRequest renameCharacterRequest = new RenameCharacterRequest(NEW_CHARACTER_NAME, CHARACTER_ID);

        when(characterQueryService.isCharNameExists(NEW_CHARACTER_NAME)).thenReturn(true);
        //WHEN
        underTest.renameCharacter(renameCharacterRequest, CHARACTER_ID);
    }

    @Test
    public void testRenameCharacterShouldRenameAndInvalidate() {
        //GIVEN
        RenameCharacterRequest renameCharacterRequest = new RenameCharacterRequest(NEW_CHARACTER_NAME, CHARACTER_ID);

        when(characterQueryService.isCharNameExists(NEW_CHARACTER_NAME)).thenReturn(false);
        when(characterQueryService.findCharacterByIdAuthorized(CHARACTER_ID, USER_ID)).thenReturn(character);
        //WHEN
        SkyXpCharacter result = underTest.renameCharacter(renameCharacterRequest, USER_ID);
        //THEN
        verify(characterQueryService).isCharNameExists(NEW_CHARACTER_NAME);
        verify(characterQueryService).findCharacterByIdAuthorized(CHARACTER_ID, USER_ID);
        verify(characterDao).save(character);
        verify(characterNameExistsCache).invalidate(NEW_CHARACTER_NAME);
        assertThat(result).isEqualTo(character);
        verify(character).setCharacterName(NEW_CHARACTER_NAME);
    }
}