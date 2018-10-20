package selenium.cases.characterselect;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import selenium.cases.characterselect.testcase.CharacterNameTest;
import selenium.domain.SeleniumCharacter;
import selenium.flow.Registration;
import selenium.page.CharacterSelectPage;
import selenium.validator.FieldValidator;
import selenium.validator.NotificationValidator;

import java.util.List;

import static org.junit.Assert.assertTrue;
import static selenium.util.LinkUtil.CHARACTER_SELECT;

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
        /*
        Validate charactername
            - too short
            - too long
            - existing
        Rename character
            - too short
            - too long
            - existing
        Delete character
        Select character
         */
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
    }

    private void init() {
        new Registration(driver).registerUser();
        createCharacter(registeredCharacter);
    }

    private void createCharacter(SeleniumCharacter character) {
        WebElement characterNameField = characterSelectPage.getNewCharacterNameField();
        characterNameField.clear();
        characterNameField.sendKeys(character.getCharacterName());

        WebElement createCharacterButton = characterSelectPage.getCreateCharacterButton();
        fieldValidator.verifySuccess(
            characterSelectPage.getInvalidNewCharacterNameField(),
            createCharacterButton
        );

        createCharacterButton.click();

        verifyCharacterCreation(character);
    }

    private void verifyCharacterCreation(SeleniumCharacter character) {
        notificationValidator.verifyNotificationVisibility("Karakter létrehozva.");

        List<WebElement> characters = characterSelectPage.getCharacterList();
        assertTrue(characters.stream()
            .anyMatch(webElement -> webElement.getText().equals(character.getCharacterName()))
        );
    }
}
