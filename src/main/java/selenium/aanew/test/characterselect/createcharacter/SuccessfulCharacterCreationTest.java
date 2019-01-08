package selenium.aanew.test.characterselect.createcharacter;

import lombok.Builder;
import selenium.aanew.logic.flow.CreateCharacter;
import selenium.aanew.test.characterselect.createcharacter.helper.CreateCharacterTestHelper;

@Builder
public class SuccessfulCharacterCreationTest {
    private final CreateCharacterTestHelper createCharacterTestHelper;
    private final CreateCharacter createCharacter;

    public void testSuccessfulCharacterCreation() {
        createCharacterTestHelper.registerUser();

        createCharacter.createCharacter();
    }
}
