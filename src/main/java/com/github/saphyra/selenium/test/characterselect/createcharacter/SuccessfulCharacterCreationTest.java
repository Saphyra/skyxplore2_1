package com.github.saphyra.selenium.test.characterselect.createcharacter;

import lombok.Builder;
import com.github.saphyra.selenium.test.characterselect.common.CharacterSelectTestHelper;

@Builder
public class SuccessfulCharacterCreationTest {
    private final CharacterSelectTestHelper characterSelectTestHelper;

    public void testSuccessfulCharacterCreation() {
        characterSelectTestHelper.registerUser();

        characterSelectTestHelper.createCharacter();
    }
}
