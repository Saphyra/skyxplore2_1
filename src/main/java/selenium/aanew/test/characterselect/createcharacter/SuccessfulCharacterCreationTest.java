package selenium.aanew.test.characterselect.createcharacter;

import lombok.Builder;
import selenium.aanew.test.characterselect.createcharacter.helper.CreateCharacterTestHelper;

@Builder
public class SuccessfulCharacterCreationTest {
    private final CreateCharacterTestHelper createCharacterTestHelper;

    public void testSuccessfulCharacterCreation() {
        createCharacterTestHelper.registerUser();

        createCharacterTestHelper.createCharacter();
    }
}
