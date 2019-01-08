package selenium.aaold.cases.characterselect.testcase;

import static selenium.aanew.logic.util.Util.cleanNotifications;
import static skyxplore.controller.request.character.CreateCharacterRequest.CHARACTER_NAME_MAX_LENGTH;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import lombok.Builder;
import selenium.aanew.logic.domain.SeleniumCharacter;
import selenium.aanew.logic.page.CharacterSelectPage;
import selenium.aanew.logic.validator.FieldValidator;
import selenium.aanew.logic.validator.NotificationValidator;

@Builder
public class CharacterNameTest {
    private static final String TOO_LONG_CHARACTER_NAME;

    static {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i <= CHARACTER_NAME_MAX_LENGTH + 1; i++) {
            builder.append("a");
        }
        TOO_LONG_CHARACTER_NAME = builder.toString();
    }


    private static final String ERROR_MESSAGE_CHARACTER_NAME_TOO_LONG = "Karakternév túl hosszú. (Maximum 30 karakter)";
    private static final String ERROR_MESSAGE_CHARACTER_NAME_ALREADY_EXISTS = "Karakternév foglalt.";

    private final WebDriver driver;
    private final CharacterSelectPage characterSelectPage;
    private final FieldValidator fieldValidator;
    private final SeleniumCharacter registeredCharacter;
    private final NotificationValidator notificationValidator;

    public void testTooLongCharacterName(){
        init();
        WebElement newCharacterNameField = characterSelectPage.getNewCharacterNameField();
        newCharacterNameField.sendKeys(TOO_LONG_CHARACTER_NAME);

        fieldValidator.verifyError(
            characterSelectPage.getInvalidNewCharacterNameField(),
            ERROR_MESSAGE_CHARACTER_NAME_TOO_LONG,
            newCharacterNameField,
            characterSelectPage.getCreateCharacterButton()
        );
    }

    public void testExistingCharacterName() {
        init();
        WebElement newCharacterNameField = characterSelectPage.getNewCharacterNameField();
        newCharacterNameField.sendKeys(registeredCharacter.getCharacterName());

        fieldValidator.verifyError(
            characterSelectPage.getInvalidNewCharacterNameField(),
            ERROR_MESSAGE_CHARACTER_NAME_ALREADY_EXISTS,
            newCharacterNameField,
            characterSelectPage.getCreateCharacterButton()
        );
    }

    private void init() {
        characterSelectPage.getNewCharacterNameField().clear();
    }

    public void cleanUp(){
        cleanNotifications(driver);
    }
}
