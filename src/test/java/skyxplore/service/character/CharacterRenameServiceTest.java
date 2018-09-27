package skyxplore.service.character;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static skyxplore.testutil.TestUtils.CHARACTER_ID_1;
import static skyxplore.testutil.TestUtils.CHARACTER_NEW_NAME;
import static skyxplore.testutil.TestUtils.createCharacter;
import static skyxplore.testutil.TestUtils.createRenameCharacterRequest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.google.common.cache.Cache;
import skyxplore.controller.request.character.RenameCharacterRequest;
import skyxplore.dataaccess.db.CharacterDao;
import skyxplore.domain.character.SkyXpCharacter;
import skyxplore.exception.CharacterNameAlreadyExistsException;

@RunWith(MockitoJUnitRunner.class)
public class CharacterRenameServiceTest {
    @Mock
    private  CharacterDao characterDao;

    @Mock
    private  Cache<String, Boolean> characterNameCache;

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
        when(characterQueryService.findByCharacterId(CHARACTER_ID_1)).thenReturn(character);
        //WHEN
        underTest.renameCharacter(renameCharacterRequest, CHARACTER_ID_1);
        //THEN
        verify(characterQueryService).isCharNameExists(CHARACTER_NEW_NAME);
        verify(characterQueryService).findByCharacterId(CHARACTER_ID_1);
        verify(characterDao).save(character);
        verify(characterNameCache).invalidate(CHARACTER_NEW_NAME);
        assertEquals(CHARACTER_NEW_NAME, character.getCharacterName());
    }
}