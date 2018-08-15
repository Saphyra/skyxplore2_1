package skyxplore.controller.view.character;

import static org.junit.Assert.assertEquals;
import static skyxplore.TestUtils.CHARACTER_ID;
import static skyxplore.TestUtils.CHARACTER_NAME;
import static skyxplore.TestUtils.createCharacter;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class CharacterViewConverterTest {
    @InjectMocks
    private CharacterViewConverter underTest;

    @Test
    public void testConvertShouldReturnView(){
        //WHEN
        CharacterView view = underTest.convertDomain(createCharacter());
        //THEN
        assertEquals(CHARACTER_ID, view.getCharacterId());
        assertEquals(CHARACTER_NAME, view.getCharacterName());
    }
}
