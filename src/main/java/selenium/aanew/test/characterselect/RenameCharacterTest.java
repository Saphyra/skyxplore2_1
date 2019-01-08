package selenium.aanew.test.characterselect;

import org.junit.Test;
import selenium.aanew.SeleniumTestApplication;
import selenium.aanew.logic.flow.CreateCharacter;
import selenium.aanew.logic.flow.Registration;
import selenium.aanew.logic.page.CharacterSelectPage;
import selenium.aanew.logic.validator.FieldValidator;
import selenium.aanew.test.characterselect.renamecharacter.ExistingCharacterNameTest;
import selenium.aanew.test.characterselect.renamecharacter.TooLongCharacterNameTest;
import selenium.aanew.test.characterselect.renamecharacter.TooShortCharacterNameTest;
import selenium.aanew.test.characterselect.renamecharacter.helper.RenameCharacterTestHelper;

import static selenium.aanew.logic.util.LinkUtil.CHARACTER_SELECT;

public class RenameCharacterTest extends SeleniumTestApplication {
    private RenameCharacterTestHelper renameCharacterTestHelper;
    private FieldValidator fieldValidator;
    private CharacterSelectPage characterSelectPage;

    @Override
    protected void init() {
        /*
        test.testExistingCharacterName();
        test.testRename();
         */

        characterSelectPage = new CharacterSelectPage(driver);
        renameCharacterTestHelper = new RenameCharacterTestHelper(
            new Registration(driver),
            new CreateCharacter(driver),
            characterSelectPage
        );
        fieldValidator = new FieldValidator(driver, CHARACTER_SELECT);
    }

    @Test
    public void testTooShortCharacterName() {
        TooShortCharacterNameTest.builder()
            .renameCharacterTestHelper(renameCharacterTestHelper)
            .characterSelectPage(characterSelectPage)
            .fieldValidator(fieldValidator)
            .build()
            .testTooShortCharacterName();
    }

    @Test
    public void testTooLongCharacterName(){
        TooLongCharacterNameTest.builder()
            .renameCharacterTestHelper(renameCharacterTestHelper)
            .characterSelectPage(characterSelectPage)
            .fieldValidator(fieldValidator)
            .build()
            .testTooLongCharacterName();
    }

    @Test
    public void testExistingCharacterName(){
        ExistingCharacterNameTest.builder()
            .renameCharacterTestHelper(renameCharacterTestHelper)
            .characterSelectPage(characterSelectPage)
            .fieldValidator(fieldValidator)
            .build()
            .testExistingCharacterName();
    }
}
