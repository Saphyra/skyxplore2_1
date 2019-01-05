package selenium.aaold.cases.characterselect;

import org.openqa.selenium.WebDriver;
import selenium.aanew.flow.Registration;
import selenium.aanew.validator.FieldValidator;
import selenium.aanew.validator.NotificationValidator;
import selenium.aaold.cases.characterselect.testcase.CharacterNameTest;
import selenium.aaold.cases.characterselect.testcase.DeleteCharacterTest;
import selenium.aaold.cases.characterselect.testcase.RenameCharacterTest;
import selenium.aaold.cases.characterselect.testcase.SelectCharacterTest;
import selenium.aanew.domain.SeleniumCharacter;
import selenium.aanew.flow.CreateCharacter;
import selenium.aanew.page.CharacterSelectPage;

import static selenium.aanew.util.LinkUtil.CHARACTER_SELECT;

public class CharacterSelectTest {

    private final WebDriver driver;
    private final CharacterSelectPage characterSelectPage;
    private final SeleniumCharacter registeredCharacter;
    private final FieldValidator fieldValidator;
    private final NotificationValidator notificationValidator;

    private CharacterSelectTest(WebDriver driver) {
        this.driver = driver;
        this.characterSelectPage = new CharacterSelectPage(driver);
        this.registeredCharacter = SeleniumCharacter.create();
        this.fieldValidator = new FieldValidator(driver, CHARACTER_SELECT);
        this.notificationValidator = new NotificationValidator(driver);
    }

    public static void run(WebDriver driver) {
        CharacterSelectTest testCase = new CharacterSelectTest(driver);
        testCase.init();
        testCase.validateCharacterName();
        testCase.validateRenameCharacter();
        testCase.validateDeleteCharacter();
        testCase.validateSelectCharacter();
    }

    private void validateCharacterName() {
        CharacterNameTest test = CharacterNameTest.builder()
            .driver(driver)
            .characterSelectPage(characterSelectPage)
            .fieldValidator(fieldValidator)
            .notificationValidator(notificationValidator)
            .registeredCharacter(registeredCharacter)
            .build();

        test.testTooShortCharacterName();
        test.testTooLongCharacterName();
        test.testExistingCharacterName();
        test.cleanUp();
    }

    private void validateRenameCharacter() {
        RenameCharacterTest test = RenameCharacterTest.builder()
            .driver(driver)
            .characterSelectPage(characterSelectPage)
            .fieldValidator(fieldValidator)
            .notificationValidator(notificationValidator)
            .registeredCharacter(registeredCharacter)
            .build();

        test.init();
        test.testTooShortCharacterName();
        test.testTooLongCharacterName();
        test.testExistingCharacterName();
        test.testRename();
    }

    private void validateDeleteCharacter() {
        DeleteCharacterTest test = DeleteCharacterTest.builder()
            .driver(driver)
            .characterSelectPage(characterSelectPage)
            .notificationValidator(notificationValidator)
            .build();

        test.setUp();
        test.testNotDeleteWhenCancel();
        test.testDelete();
    }

    private void validateSelectCharacter() {
        SelectCharacterTest testCase = SelectCharacterTest.builder()
            .driver(driver)
            .characterSelectPage(characterSelectPage)
            .build();

        testCase.testSelectCharacter();
    }

    private void init() {
        new Registration(driver).registerUser();
        new CreateCharacter(driver).createCharacter(registeredCharacter);
    }
}
