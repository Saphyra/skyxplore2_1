package selenium.aanew.test.characterselect;

import static selenium.aanew.logic.util.LinkUtil.CHARACTER_SELECT;

import org.junit.Test;

import selenium.aanew.SeleniumTestApplication;
import selenium.aanew.logic.flow.CreateCharacter;
import selenium.aanew.logic.flow.Registration;
import selenium.aanew.logic.page.CharacterSelectPage;
import selenium.aanew.logic.validator.FieldValidator;
import selenium.aanew.test.characterselect.createcharacter.SuccessfulCharacterCreationTest;
import selenium.aanew.test.characterselect.createcharacter.TooLongCharacterNameTest;
import selenium.aanew.test.characterselect.createcharacter.TooShortCharacterNameTest;
import selenium.aanew.test.characterselect.createcharacter.helper.CreateCharacterTestHelper;

public class CreateCharacterTest extends SeleniumTestApplication {
    private CreateCharacterTestHelper createCharacterTestHelper;
    private CharacterSelectPage characterSelectPage;
    private FieldValidator fieldValidator;
    private CreateCharacter createCharacter;

    @Override
    protected void init() {
        /*
        test.testTooLongCharacterName();
        test.testExistingCharacterName();
        testSuccessful()
         */

        characterSelectPage = new CharacterSelectPage(driver);
        createCharacterTestHelper = new CreateCharacterTestHelper(new Registration(driver));
        fieldValidator = new FieldValidator(driver, CHARACTER_SELECT);
        createCharacter = new CreateCharacter(driver);
    }

    @Test
    public void testTooShortCharacterName() {
        TooShortCharacterNameTest.builder()
            .createCharacterTestHelper(createCharacterTestHelper)
            .characterSelectPage(characterSelectPage)
            .fieldValidator(fieldValidator)
            .build()
            .testTooShortCharacterName();
    }

    @Test
    public void testTooLongCharacterName() {
        TooLongCharacterNameTest.builder()
            .createCharacterTestHelper(createCharacterTestHelper)
            .characterSelectPage(characterSelectPage)
            .fieldValidator(fieldValidator)
            .build()
            .testTooLongCharacterName();
    }

    @Test
    public void testSuccessfulCharacterCreation() {
        SuccessfulCharacterCreationTest.builder()
            .createCharacterTestHelper(createCharacterTestHelper)
            .createCharacter(createCharacter)
            .build()
            .testSuccessfulCharacterCreation();
    }
}
