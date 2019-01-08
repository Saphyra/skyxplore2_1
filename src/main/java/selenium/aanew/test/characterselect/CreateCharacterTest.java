package selenium.aanew.test.characterselect;

import static selenium.aanew.logic.util.LinkUtil.CHARACTER_SELECT;

import org.junit.Test;

import selenium.aanew.SeleniumTestApplication;
import selenium.aanew.logic.flow.Registration;
import selenium.aanew.logic.page.CharacterSelectPage;
import selenium.aanew.logic.validator.FieldValidator;
import selenium.aanew.test.characterselect.createcharacter.TooShortCharacterNameTest;
import selenium.aanew.test.characterselect.createcharacter.helper.CreateCharacterTestHelper;

public class CreateCharacterTest extends SeleniumTestApplication {
    private  CreateCharacterTestHelper createCharacterTestHelper;
    private  CharacterSelectPage characterSelectPage;
    private  FieldValidator fieldValidator;

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
    }

    @Test
    public void testTooShortCharacterName(){
        TooShortCharacterNameTest.builder()
            .createCharacterTestHelper(createCharacterTestHelper)
            .characterSelectPage(characterSelectPage)
            .fieldValidator(fieldValidator)
            .build()
            .testTooShortCharacterName();
    }
}
