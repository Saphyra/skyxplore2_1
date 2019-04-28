package skyxplore.service.character;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static skyxplore.testutil.TestUtils.CHARACTER_ID_1;
import static skyxplore.testutil.TestUtils.USER_ID;
import static skyxplore.testutil.TestUtils.createCharacter;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import org.github.saphyra.skyxplore.character.repository.CharacterDao;
import org.github.saphyra.skyxplore.character.domain.SkyXpCharacter;

@RunWith(MockitoJUnitRunner.class)
public class CharacterDeleteServiceTest {
    @Mock
    private CharacterDao characterDao;

    @Mock
    private CharacterQueryService characterQueryService;

    @InjectMocks
    private CharacterDeleteService underTest;

    @Test
    public void testDeleteCharacterShouldDelete() {
        //GIVEN
        SkyXpCharacter character = createCharacter();
        when(characterQueryService.findCharacterByIdAuthorized(CHARACTER_ID_1, USER_ID)).thenReturn(character);
        //WHEN
        underTest.deleteCharacter(CHARACTER_ID_1, USER_ID);
        //THEN
        verify(characterQueryService).findCharacterByIdAuthorized(CHARACTER_ID_1, USER_ID);
        verify(characterDao).deleteById(CHARACTER_ID_1);
    }
}