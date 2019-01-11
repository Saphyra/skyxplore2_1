package selenium.test.characterselect;

import org.junit.Test;
import selenium.SeleniumTestApplication;
import selenium.logic.flow.CreateCharacter;
import selenium.logic.flow.Registration;
import selenium.logic.page.CharacterSelectPage;
import selenium.logic.validator.FieldValidator;
import selenium.logic.validator.NotificationValidator;
import selenium.test.characterselect.renamecharacter.ExistingCharacterNameTest;
import selenium.test.characterselect.renamecharacter.SuccessfulCharacterRenameTest;
import selenium.test.characterselect.renamecharacter.TooLongCharacterNameTest;
import selenium.test.characterselect.renamecharacter.TooShortCharacterNameTest;
import selenium.test.characterselect.renamecharacter.helper.RenameCharacterTestHelper;

import static selenium.logic.util.LinkUtil.CHARACTER_SELECT;

public class RenameCharacterTest extends SeleniumTestApplication {
    private RenameCharacterTestHelper renameCharacterTestHelper;
    private FieldValidator fieldValidator;
    private CharacterSelectPage characterSelectPage;
    private NotificationValidator notificationValidator;

    @Override
    protected void init() {
        characterSelectPage = new CharacterSelectPage(driver);
        fieldValidator = new FieldValidator(driver, CHARACTER_SELECT);
        renameCharacterTestHelper = new RenameCharacterTestHelper(
            driver,
            new Registration(driver),
            new CreateCharacter(driver),
            characterSelectPage,
            fieldValidator
        );
        notificationValidator = new NotificationValidator(driver);
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
    public void testTooLongCharacterName() {
        TooLongCharacterNameTest.builder()
            .renameCharacterTestHelper(renameCharacterTestHelper)
            .characterSelectPage(characterSelectPage)
            .fieldValidator(fieldValidator)
            .build()
            .testTooLongCharacterName();
    }

    @Test
    public void testExistingCharacterName() {
        ExistingCharacterNameTest.builder()
            .renameCharacterTestHelper(renameCharacterTestHelper)
            .characterSelectPage(characterSelectPage)
            .fieldValidator(fieldValidator)
            .build()
            .testExistingCharacterName();
    }

    @Test
    public void testSuccessfulCharacterRename() {
        SuccessfulCharacterRenameTest.builder()
            .characterSelectPage(characterSelectPage)
            .renameCharacterTestHelper(renameCharacterTestHelper)
            .notificationValidator(notificationValidator)
            .build()
            .testSuccessfulCharacterRename();
    }
}
