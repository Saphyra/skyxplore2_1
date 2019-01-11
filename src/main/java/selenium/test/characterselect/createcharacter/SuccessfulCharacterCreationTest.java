package selenium.test.characterselect.createcharacter;

import lombok.Builder;
import selenium.test.characterselect.common.CharacterSelectTestHelper;

@Builder
public class SuccessfulCharacterCreationTest {
    private final CharacterSelectTestHelper characterSelectTestHelper;

    public void testSuccessfulCharacterCreation() {
        characterSelectTestHelper.registerUser();

        characterSelectTestHelper.createCharacter();
    }
}
