package selenium.aanew.test.characterselect;

import static selenium.aanew.logic.util.LinkUtil.CHARACTER_SELECT;

import org.junit.Test;

import selenium.aanew.SeleniumTestApplication;
import selenium.aanew.logic.flow.CreateCharacter;
import selenium.aanew.logic.flow.Registration;
import selenium.aanew.logic.page.CharacterSelectPage;
import selenium.aanew.logic.validator.FieldValidator;
import selenium.aanew.test.characterselect.createcharacter.ExistingCharacterNameTest;
import selenium.aanew.test.characterselect.createcharacter.SuccessfulCharacterCreationTest;
import selenium.aanew.test.characterselect.createcharacter.TooLongCharacterNameTest;
import selenium.aanew.test.characterselect.createcharacter.TooShortCharacterNameTest;
import selenium.aanew.test.characterselect.common.CharacterSelectTestHelper;

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
            .build()
            .testTooShortCharacterName();
    }

    @Test
    public void testTooLongCharacterName() {
        TooLongCharacterNameTest.builder()
            .characterSelectTestHelper(characterSelectTestHelper)
            .characterSelectPage(characterSelectPage)
            .fieldValidator(fieldValidator)
            .build()
            .testTooLongCharacterName();
    }

    @Test
    public void testExistingCharacterName() {
        ExistingCharacterNameTest.builder()
            .characterSelectTestHelper(characterSelectTestHelper)
            .characterSelectPage(characterSelectPage)
            .fieldValidator(fieldValidator)
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
