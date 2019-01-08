package selenium.aaold.cases.characterselect;

import org.openqa.selenium.WebDriver;
import selenium.aanew.logic.domain.SeleniumCharacter;
import selenium.aanew.logic.flow.CreateCharacter;
import selenium.aanew.logic.flow.Registration;
import selenium.aanew.logic.page.CharacterSelectPage;
import selenium.aanew.logic.validator.FieldValidator;
import selenium.aanew.logic.validator.NotificationValidator;
import selenium.aaold.cases.characterselect.testcase.SelectCharacterTest;

import static selenium.aanew.logic.util.LinkUtil.CHARACTER_SELECT;

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
        testCase.validateSelectCharacter();
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
