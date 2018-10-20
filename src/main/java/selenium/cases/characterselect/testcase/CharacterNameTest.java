package selenium.cases.characterselect.testcase;

import lombok.Builder;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import selenium.domain.SeleniumCharacter;
import selenium.page.CharacterSelectPage;
import selenium.validator.FieldValidator;
import selenium.validator.NotificationValidator;

import static selenium.util.StringUtil.crop;
import static skyxplore.controller.request.character.CreateCharacterRequest.CHARACTER_NAME_MAX_LENGTH;
import static skyxplore.controller.request.character.CreateCharacterRequest.CHARACTER_NAME_MIN_LENGTH;

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

    private static final String ERROR_MESSAGE_CHARACTER_NAME_TOO_SHORT = "Karakternév túl rövid. (Minimum 3 karakter)";
    private static final String ERROR_MESSAGE_CHARACTER_NAME_TOO_LONG = "Karakternév túl hosszú. (Maximum 30 karakter)";
    private static final String ERROR_MESSAGE_CHARACTER_NAME_ALREADY_EXISTS = "Karakternév foglalt.";

    private final WebDriver driver;
    private final CharacterSelectPage characterSelectPage;
    private final FieldValidator fieldValidator;
    private final SeleniumCharacter registeredCharacter;
    private final NotificationValidator notificationValidator;

    public void testTooShortCharacterName() {
        init();
        WebElement newCharacterNameField = characterSelectPage.getNewCharacterNameField();
        newCharacterNameField.sendKeys(crop(SeleniumCharacter.createRandomCharacterName(), CHARACTER_NAME_MIN_LENGTH - 1));

        fieldValidator.verifyError(
            characterSelectPage.getInvalidNewCharacterNameField(),
            ERROR_MESSAGE_CHARACTER_NAME_TOO_SHORT,
            newCharacterNameField,
            characterSelectPage.getCreateCharacterButton()
        );
    }

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
}
