package selenium.aaold.cases.characterselect.testcase;

import static selenium.aanew.logic.util.Util.cleanNotifications;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import lombok.Builder;
import selenium.aanew.logic.domain.SeleniumCharacter;
import selenium.aanew.logic.page.CharacterSelectPage;
import selenium.aanew.logic.validator.FieldValidator;
import selenium.aanew.logic.validator.NotificationValidator;

@Builder
public class CharacterNameTest {




    private static final String ERROR_MESSAGE_CHARACTER_NAME_ALREADY_EXISTS = "Karakternév foglalt.";

    private final WebDriver driver;
    private final CharacterSelectPage characterSelectPage;
    private final FieldValidator fieldValidator;
    private final SeleniumCharacter registeredCharacter;
    private final NotificationValidator notificationValidator;

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
