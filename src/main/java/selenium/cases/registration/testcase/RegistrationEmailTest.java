package selenium.cases.registration.testcase;

import lombok.Builder;
import org.openqa.selenium.WebElement;
import selenium.cases.registration.RegistrationTest;
import selenium.cases.registration.RegistrationVerificator;
import selenium.domain.SeleniumUser;
import selenium.page.IndexPage;

@Builder
public class RegistrationEmailTest {
    private static final String INVALID_EMAIL = "abcc";

    private static final String ERROR_MESSAGE_INVALID_EMAIL = "Érvénytelen e-mail cím.";
    private static final String ERROR_MESSAGE_EXISTING_EMAIL = "Már van regisztrált felhasználó a megadott e-mail címmel.";

    private final IndexPage indexPage;
    private final RegistrationVerificator registrationVerificator;
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

        registrationVerificator.verifyRegistrationError(
            indexPage.getInvalidEmailField(),
            ERROR_MESSAGE_INVALID_EMAIL,
            emailField
        );
    }

    private void validateExistingEmail() {
        setUpForEmailValidation();

        WebElement emailField = indexPage.getRegistrationEmailField();
        emailField.sendKeys(currentUser.getEmail());

        registrationVerificator.verifyRegistrationError(
            indexPage.getInvalidEmailField(),
            ERROR_MESSAGE_EXISTING_EMAIL,
            emailField
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
    }
}
