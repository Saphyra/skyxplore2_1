package selenium.cases.registration.testcase;

import lombok.Builder;
import org.openqa.selenium.WebElement;
import selenium.cases.registration.RegistrationTest;
import selenium.cases.registration.RegistrationVerificator;
import selenium.domain.SeleniumUser;
import selenium.page.IndexPage;

import static selenium.util.StringUtil.crop;
import static skyxplore.controller.request.user.UserRegistrationRequest.PASSWORD_MAX_LENGTH;
import static skyxplore.controller.request.user.UserRegistrationRequest.PASSWORD_MIN_LENGTH;

@Builder
public class RegistrationPasswordTest {
    private static final String TOO_LONG_PASSWORD;
    static {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i <= PASSWORD_MAX_LENGTH + 1; i++) {
            builder.append("a");
        }
        TOO_LONG_PASSWORD = builder.toString();
    }

    private static final String ERROR_MESSAGE_TOO_SHORT_PASSWORD = "Jelszó túl rövid (Minimum 6 karakter).";
    private static final String ERROR_MESSAGE_TOO_LONG_PASSWORD = "Jelszó túl hosszú (Maximum 30 karakter).";
    private static final String ERROR_MESSAGE_BAD_CONFIRM_PASSWORD = "A jelszavak nem egyeznek.";

    private final IndexPage indexPage;
    private final RegistrationVerificator registrationVerificator;
    private final RegistrationTest registrationTest;
    private final SeleniumUser currentUser;
    private final SeleniumUser newUser;

    public void validatePasswords() {
        validateTooShortPassword();
        validateTooLongPassword();
        validateConfirmPassword();
    }

    private void validateTooShortPassword() {
        setUpForPasswordValidation();

        WebElement passwordField = indexPage.getRegistrationPasswordField();
        passwordField.sendKeys(crop(newUser.getPassword(), PASSWORD_MIN_LENGTH - 1));

        registrationVerificator.verifyRegistrationError(
            indexPage.getInvalidPasswordField(),
            ERROR_MESSAGE_TOO_SHORT_PASSWORD,
            passwordField
        );
    }

    private void validateTooLongPassword() {
        setUpForPasswordValidation();

        WebElement passwordField = indexPage.getRegistrationPasswordField();
        passwordField.sendKeys(TOO_LONG_PASSWORD);

        registrationVerificator.verifyRegistrationError(
            indexPage.getInvalidPasswordField(),
            ERROR_MESSAGE_TOO_LONG_PASSWORD,
            passwordField
        );
    }

    private void validateConfirmPassword() {
        setUpForPasswordValidation();

        WebElement passwordField = indexPage.getRegistrationPasswordField();
        passwordField.sendKeys(newUser.getPassword());

        WebElement confirmPasswordField = indexPage.getRegistrationConfirmPasswordField();
        confirmPasswordField.sendKeys(currentUser.getPassword());

        registrationVerificator.verifyRegistrationError(
            indexPage.getInvalidPasswordField(),
            ERROR_MESSAGE_BAD_CONFIRM_PASSWORD,
            passwordField
        );

        registrationVerificator.verifyRegistrationError(
            indexPage.getInvalidConfirmPasswordField(),
            ERROR_MESSAGE_BAD_CONFIRM_PASSWORD,
            confirmPasswordField
        );
    }

    private void setUpForPasswordValidation() {
        registrationTest.cleanUp();

        WebElement userNameField = indexPage.getRegistrationUserNameField();
        userNameField.sendKeys(newUser.getUserName());

        WebElement emailElement = indexPage.getRegistrationEmailField();
        emailElement.sendKeys(newUser.getEmail());
    }
}
