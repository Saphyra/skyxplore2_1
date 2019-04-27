package skyxplore.controller.view.character;

import static org.junit.Assert.assertEquals;
import static skyxplore.testutil.TestUtils.CHARACTER_ID_1;
import static skyxplore.testutil.TestUtils.CHARACTER_NAME;
import static skyxplore.testutil.TestUtils.createCharacter;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class CharacterViewConverterTest {
    @InjectMocks
    private CharacterViewConverter underTest;

    @Test
    public void testConvertShouldReturnView() {
        //WHEN
        CharacterView view = underTest.convertDomain(createCharacter());
        //THEN
        assertEquals(CHARACTER_ID_1, view.getCharacterId());
        assertEquals(CHARACTER_NAME, view.getCharacterName());
    }
}
