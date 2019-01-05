package selenium.aaold.cases.registration.testcase;

import lombok.Builder;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import selenium.aanew.logic.domain.SeleniumUser;
import selenium.aanew.logic.page.IndexPage;
import selenium.aanew.logic.validator.FieldValidator;
import selenium.aaold.cases.registration.RegistrationTest;

import static selenium.aanew.logic.util.Util.crop;
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

    private final WebDriver driver;
    private final IndexPage indexPage;
    private final FieldValidator fieldValidator;
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

        fieldValidator.verifyError(
            indexPage.getInvalidPasswordField(),
            ERROR_MESSAGE_TOO_SHORT_PASSWORD,
            passwordField,
            indexPage.getRegisterButton()
        );
    }

    private void validateTooLongPassword() {
        setUpForPasswordValidation();

        WebElement passwordField = indexPage.getRegistrationPasswordField();
        passwordField.sendKeys(TOO_LONG_PASSWORD);

        fieldValidator.verifyError(
            indexPage.getInvalidPasswordField(),
            ERROR_MESSAGE_TOO_LONG_PASSWORD,
            passwordField,
            indexPage.getRegisterButton()
        );
    }

    private void validateConfirmPassword() {
        setUpForPasswordValidation();

        WebElement passwordField = indexPage.getRegistrationPasswordField();
        passwordField.sendKeys(newUser.getPassword());

        WebElement confirmPasswordField = indexPage.getRegistrationConfirmPasswordField();
        confirmPasswordField.sendKeys(currentUser.getPassword());

        fieldValidator.verifyError(
            indexPage.getInvalidPasswordField(),
            ERROR_MESSAGE_BAD_CONFIRM_PASSWORD,
            passwordField,
            indexPage.getRegisterButton()
        );

        fieldValidator.verifyError(
            indexPage.getInvalidConfirmPasswordField(),
            ERROR_MESSAGE_BAD_CONFIRM_PASSWORD,
            confirmPasswordField,
            indexPage.getRegisterButton()
        );
    }

    private void setUpForPasswordValidation() {
        registrationTest.cleanUp();

        WebElement userNameField = indexPage.getRegistrationUserNameField();
        userNameField.sendKeys(newUser.getUserName());

        WebElement emailElement = indexPage.getRegistrationEmailField();
        emailElement.sendKeys(newUser.getEmail());

        WebDriverWait webDriverWait = new WebDriverWait(driver, 10);
        webDriverWait.until(ExpectedConditions.invisibilityOf(indexPage.getInvalidUserNameField()));
        webDriverWait.until(ExpectedConditions.invisibilityOf(indexPage.getInvalidEmailField()));
    }
}
