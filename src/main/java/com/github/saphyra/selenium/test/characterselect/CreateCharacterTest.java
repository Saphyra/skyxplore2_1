package com.github.saphyra.selenium.test.characterselect;

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
import org.junit.Test;

import static com.github.saphyra.selenium.logic.util.LinkUtil.CHARACTER_SELECT;

public class CreateCharacterTest extends SeleniumTestApplication {
    private CharacterSelectTestHelper characterSelectTestHelper;
    private CharacterSelectPage characterSelectPage;
    private FieldValidator fieldValidator;

    @Override
    protected void init() {
        characterSelectPage = new CharacterSelectPage(driver);
        characterSelectTestHelper = new CharacterSelectTestHelper(new Registration(driver), new CreateCharacter(driver));
        fieldValidator = new FieldValidator(driver, CHARACTER_SELECT);
    }

    @Test
    public void testTooShortCharacterName() {
        TooShortCharacterNameTest.builder()
            .characterSelectTestHelper(characterSelectTestHelper)
            .characterSelectPage(characterSelectPage)
            .fieldValidator(fieldValidator)
            .driver(driver)
            .build()
            .testTooShortCharacterName();
    }

    @Test
    public void testTooLongCharacterName() {
        TooLongCharacterNameTest.builder()
            .characterSelectTestHelper(characterSelectTestHelper)
            .characterSelectPage(characterSelectPage)
            .fieldValidator(fieldValidator)
            .driver(driver)
            .build()
            .testTooLongCharacterName();
    }

    @Test
    public void testExistingCharacterName() {
        ExistingCharacterNameTest.builder()
            .characterSelectTestHelper(characterSelectTestHelper)
            .characterSelectPage(characterSelectPage)
            .fieldValidator(fieldValidator)
            .driver(driver)
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
