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

@Builder
public class RegistrationEmailTest {
    private static final String ERROR_MESSAGE_EXISTING_EMAIL = "Már van regisztrált felhasználó a megadott e-mail címmel.";

    private final WebDriver driver;
    private final IndexPage indexPage;
    private final FieldValidator fieldValidator;
    private final RegistrationTest registrationTest;
    private final SeleniumUser currentUser;
    private final SeleniumUser newUser;

    public void validateEmail(){
        validateExistingEmail();
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
