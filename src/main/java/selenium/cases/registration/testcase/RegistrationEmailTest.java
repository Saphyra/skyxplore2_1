package selenium.cases.registration.testcase;

import lombok.Builder;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import selenium.cases.registration.RegistrationTest;
import selenium.domain.SeleniumUser;
import selenium.page.IndexPage;
import selenium.validator.FieldValidator;

@Builder
public class RegistrationEmailTest {
    private static final String INVALID_EMAIL = "abcc";

    private static final String ERROR_MESSAGE_INVALID_EMAIL = "Érvénytelen e-mail cím.";
    private static final String ERROR_MESSAGE_EXISTING_EMAIL = "Már van regisztrált felhasználó a megadott e-mail címmel.";

    private final WebDriver driver;
    private final IndexPage indexPage;
    private final FieldValidator fieldValidator;
    private final RegistrationTest registrationTest;
    private final SeleniumUser currentUser;
    private final SeleniumUser newUser;

    public void validateEmail(){
        validateInvalidEmail();
        validateExistingEmail();
    }

    private void validateInvalidEmail() {
        setUpForEmailValidation();

        WebElement emailField = indexPage.getRegistrationEmailField();
        emailField.sendKeys(INVALID_EMAIL);

        fieldValidator.verifyError(
            indexPage.getInvalidEmailField(),
            ERROR_MESSAGE_INVALID_EMAIL,
            emailField,
            indexPage.getRegisterButton()
        );
    }

    private void validateExistingEmail() {
        setUpForEmailValidation();

        WebElement emailField = indexPage.getRegistrationEmailField();
        emailField.sendKeys(currentUser.getEmail());

        fieldValidator.verifyError(
            indexPage.getInvalidEmailField(),
            ERROR_MESSAGE_EXISTING_EMAIL,
            emailField,
            indexPage.getRegisterButton()
        );
    }

    private void setUpForEmailValidation() {
        registrationTest.cleanUp();

        WebElement userNameField = indexPage.getRegistrationUserNameField();
        userNameField.sendKeys(newUser.getUserName());

        WebElement passwordElement = indexPage.getRegistrationPasswordField();
        passwordElement.sendKeys(newUser.getPassword());

        WebElement confirmPasswordElement = indexPage.getRegistrationConfirmPasswordField();
        confirmPasswordElement.sendKeys(newUser.getPassword());

        WebDriverWait webDriverWait = new WebDriverWait(driver, 10);
        webDriverWait.until(ExpectedConditions.invisibilityOf(indexPage.getInvalidUserNameField()));
        webDriverWait.until(ExpectedConditions.invisibilityOf(indexPage.getInvalidPasswordField()));
        webDriverWait.until(ExpectedConditions.invisibilityOf(indexPage.getInvalidConfirmPasswordField()));
    }
}
