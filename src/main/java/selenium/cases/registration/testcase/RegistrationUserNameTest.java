package selenium.cases.registration.testcase;

import lombok.Builder;
import org.openqa.selenium.WebElement;
import selenium.cases.registration.RegistrationTest;
import selenium.cases.registration.RegistrationVerificator;
import selenium.domain.SeleniumUser;
import selenium.page.IndexPage;

import static selenium.util.StringUtil.crop;
import static skyxplore.controller.request.user.UserRegistrationRequest.USER_NAME_MAX_LENGTH;
import static skyxplore.controller.request.user.UserRegistrationRequest.USER_NAME_MIN_LENGTH;

@Builder
public class RegistrationUserNameTest {
    private static final String TOO_LONG_USER_NAME;

    static {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i <= USER_NAME_MAX_LENGTH + 1; i++) {
            builder.append("a");
        }
        TOO_LONG_USER_NAME = builder.toString();
    }

    private static final String ERROR_MESSAGE_TOO_SHORT_USER_NAME = "Felhasználónév túl rövid (Minimum 3 karakter).";
    private static final String ERROR_MESSAGE_TOO_LONG_USER_NAME = "Felhasználónév túl hosszú (Maximum 30 karakter).";
    private static final String ERROR_MESSAGE_EXISTING_USER_NAME = "Felhasználónév foglalt.";

    private final IndexPage indexPage;
    private final RegistrationVerificator registrationVerificator;
    private final RegistrationTest registrationTest;
    private final SeleniumUser currentUser;
    private final SeleniumUser newUser;

    public void validateUserName() {
        validateTooShortUserName();
        validateTooLongUserName();
        validateExistingUserName();
    }

    private void validateTooShortUserName() {
        setUpForUserNameValidation();

        WebElement userNameField = indexPage.getRegistrationUserNameField();
        userNameField.sendKeys(crop(newUser.getUserName(), USER_NAME_MIN_LENGTH - 1));

        registrationVerificator.verifyRegistrationError(
            indexPage.getInvalidUserNameField(),
            ERROR_MESSAGE_TOO_SHORT_USER_NAME,
            userNameField
        );
    }

    private void validateTooLongUserName() {
        setUpForUserNameValidation();

        WebElement userNameField = indexPage.getRegistrationUserNameField();
        userNameField.sendKeys(TOO_LONG_USER_NAME);

        registrationVerificator.verifyRegistrationError(
            indexPage.getInvalidUserNameField(),
            ERROR_MESSAGE_TOO_LONG_USER_NAME,
            userNameField
        );
    }

    private void validateExistingUserName() {
        setUpForUserNameValidation();

        WebElement userNameField = indexPage.getRegistrationUserNameField();
        userNameField.sendKeys(currentUser.getUserName());

        registrationVerificator.verifyRegistrationError(
            indexPage.getInvalidUserNameField(),
            ERROR_MESSAGE_EXISTING_USER_NAME,
            userNameField
        );
    }

    private void setUpForUserNameValidation() {
        registrationTest.cleanUp();

        WebElement passwordElement = indexPage.getRegistrationPasswordField();
        passwordElement.sendKeys(newUser.getPassword());

        WebElement confirmPasswordElement = indexPage.getRegistrationConfirmPasswordField();
        confirmPasswordElement.sendKeys(newUser.getPassword());

        WebElement emailElement = indexPage.getRegistrationEmailField();
        emailElement.sendKeys(newUser.getEmail());
    }
}
