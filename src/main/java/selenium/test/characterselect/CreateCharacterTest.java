package selenium.test.characterselect;

import static selenium.logic.util.LinkUtil.CHARACTER_SELECT;

import org.junit.Test;

import selenium.SeleniumTestApplication;
import selenium.logic.flow.CreateCharacter;
import selenium.logic.flow.Registration;
import selenium.logic.page.CharacterSelectPage;
import selenium.logic.validator.FieldValidator;
import selenium.test.characterselect.common.CharacterSelectTestHelper;
import selenium.test.characterselect.createcharacter.ExistingCharacterNameTest;
import selenium.test.characterselect.createcharacter.SuccessfulCharacterCreationTest;
import selenium.test.characterselect.createcharacter.TooLongCharacterNameTest;
import selenium.test.characterselect.createcharacter.TooShortCharacterNameTest;

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
