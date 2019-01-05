package selenium.aaold.cases.registration.testcase;

import lombok.Builder;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import selenium.aanew.logic.domain.SeleniumUser;
import selenium.aanew.logic.validator.FieldValidator;
import selenium.aaold.cases.registration.RegistrationTest;
import selenium.aanew.logic.page.IndexPage;

import static selenium.aanew.logic.util.StringUtil.crop;
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

    private final WebDriver driver;
    private final IndexPage indexPage;
    private final FieldValidator fieldValidator;
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

        fieldValidator.verifyError(
            indexPage.getInvalidUserNameField(),
            ERROR_MESSAGE_TOO_SHORT_USER_NAME,
            userNameField,
            indexPage.getRegisterButton()
        );
    }

    private void validateTooLongUserName() {
        setUpForUserNameValidation();

        WebElement userNameField = indexPage.getRegistrationUserNameField();
        userNameField.sendKeys(TOO_LONG_USER_NAME);

        fieldValidator.verifyError(
            indexPage.getInvalidUserNameField(),
            ERROR_MESSAGE_TOO_LONG_USER_NAME,
            userNameField,
            indexPage.getRegisterButton()
        );
    }

    private void validateExistingUserName() {
        setUpForUserNameValidation();

        WebElement userNameField = indexPage.getRegistrationUserNameField();
        userNameField.sendKeys(currentUser.getUserName());

        fieldValidator.verifyError(
            indexPage.getInvalidUserNameField(),
            ERROR_MESSAGE_EXISTING_USER_NAME,
            userNameField,
            indexPage.getRegisterButton()
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

        WebDriverWait webDriverWait = new WebDriverWait(driver, 10);
        webDriverWait.until(ExpectedConditions.invisibilityOf(indexPage.getInvalidPasswordField()));
        webDriverWait.until(ExpectedConditions.invisibilityOf(indexPage.getInvalidConfirmPasswordField()));
        webDriverWait.until(ExpectedConditions.invisibilityOf(indexPage.getInvalidEmailField()));
    }
}
