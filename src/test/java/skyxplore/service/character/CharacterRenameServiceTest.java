package skyxplore.service.character;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import skyxplore.cache.CharacterNameCache;
import skyxplore.controller.request.character.RenameCharacterRequest;
import skyxplore.dataaccess.db.CharacterDao;
import skyxplore.domain.character.SkyXpCharacter;
import skyxplore.exception.CharacterNameAlreadyExistsException;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static skyxplore.testutil.TestUtils.*;

@RunWith(MockitoJUnitRunner.class)
public class CharacterRenameServiceTest {
    @Mock
    private  CharacterDao characterDao;

    @Mock
    private CharacterNameCache characterNameCache;

    @Mock
    private  CharacterQueryService characterQueryService;

    @InjectMocks
    private CharacterRenameService underTest;

    @Test(expected = CharacterNameAlreadyExistsException.class)
    public void testRenameCharacterShouldThrowExceptionWhenCharacterNameExists(){
        //GIVEN
        RenameCharacterRequest renameCharacterRequest = createRenameCharacterRequest();

        when(characterQueryService.isCharNameExists(CHARACTER_NEW_NAME)).thenReturn(true);
        //WHEN
        underTest.renameCharacter(renameCharacterRequest, CHARACTER_ID_1);
    }

    @Test
    public void testRenameCharacterShouldRenameAndInvalidate(){
        //GIVEN
        RenameCharacterRequest renameCharacterRequest = createRenameCharacterRequest();

        when(characterQueryService.isCharNameExists(CHARACTER_NEW_NAME)).thenReturn(false);

        SkyXpCharacter character = createCharacter();
        when(characterQueryService.findCharacterByIdAuthorized(CHARACTER_ID_1, USER_ID)).thenReturn(character);
        //WHEN
        underTest.renameCharacter(renameCharacterRequest, USER_ID);
        //THEN
        verify(characterQueryService).isCharNameExists(CHARACTER_NEW_NAME);
        verify(characterQueryService).findCharacterByIdAuthorized(CHARACTER_ID_1, USER_ID);
        verify(characterDao).save(character);
        verify(characterNameCache).invalidate(CHARACTER_NEW_NAME);
        assertEquals(CHARACTER_NEW_NAME, character.getCharacterName());
    }
}