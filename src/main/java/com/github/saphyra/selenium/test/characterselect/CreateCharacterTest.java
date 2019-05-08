package com.github.saphyra.selenium.test.characterselect;

import static com.github.saphyra.selenium.logic.util.LinkUtil.CHARACTER_SELECT;

import org.junit.Test;

import com.github.saphyra.selenium.SeleniumTestApplication;
import com.github.saphyra.selenium.logic.flow.CreateCharacter;
import com.github.saphyra.selenium.logic.flow.Registration;
import com.github.saphyra.selenium.logic.page.CharacterSelectPage;
import com.github.saphyra.selenium.logic.validator.FieldValidator;
import com.github.saphyra.selenium.test.characterselect.common.CharacterSelectTestHelper;
import com.github.saphyra.selenium.test.characterselect.createcharacter.ExistingCharacterNameTest;
import com.github.saphyra.selenium.test.characterselect.createcharacter.SuccessfulCharacterCreationTest;
import com.github.saphyra.selenium.test.characterselect.createcharacter.TooLongCharacterNameTest;
import com.github.saphyra.selenium.test.characterselect.createcharacter.TooShortCharacterNameTest;

public class CreateCharacterTest extends SeleniumTestApplication {
    private CharacterSelectTestHelper characterSelectTestHelper;
    private CharacterSelectPage characterSelectPage;
    private FieldValidator fieldValidator;

    @Override
    protected void init() {
        characterSelectPage = new CharacterSelectPage(driver);
        characterSelectTestHelper = new CharacterSelectTestHelper(new Registration(driver, messageCodes), new CreateCharacter(driver, messageCodes));
        fieldValidator = new FieldValidator(driver, CHARACTER_SELECT);
    }

    @Test
    public void testTooShortCharacterName() {
        TooShortCharacterNameTest.builder()
            .characterSelectTestHelper(characterSelectTestHelper)
            .characterSelectPage(characterSelectPage)
            .fieldValidator(fieldValidator)
            .messageCodes(messageCodes)
            .build()
            .testTooShortCharacterName();
    }

    @Test
    public void testTooLongCharacterName() {
        TooLongCharacterNameTest.builder()
            .characterSelectTestHelper(characterSelectTestHelper)
            .characterSelectPage(characterSelectPage)
            .fieldValidator(fieldValidator)
            .messageCodes(messageCodes)
            .build()
            .testTooLongCharacterName();
    }

    @Test
    public void testExistingCharacterName() {
        ExistingCharacterNameTest.builder()
            .characterSelectTestHelper(characterSelectTestHelper)
            .characterSelectPage(characterSelectPage)
            .fieldValidator(fieldValidator)
            .messageCodes(messageCodes)
            .build()
            .testExistingCharacterName();
    }

    @Test
    public void testSuccessfulCharacterCreation() {
        SuccessfulCharacterCreationTest.builder()
            .characterSelectTestHelper(characterSelectTestHelper)
            .build()
            .testSuccessfulCharacterCreation();
    }
}
