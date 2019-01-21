package selenium.test.registration.password;

import lombok.Builder;
import org.openqa.selenium.WebElement;
import selenium.logic.domain.SeleniumUser;
import selenium.logic.page.IndexPage;
import selenium.logic.validator.FieldValidator;
import selenium.test.registration.password.helper.PasswordTestHelper;

@Builder
public class ConfirmPasswordTest {
    private static final String ERROR_MESSAGE_BAD_CONFIRM_PASSWORD = "A  nem egyeznek.";

    private final PasswordTestHelper passwordTestHelper;
    private final IndexPage indexPage;
    private final FieldValidator fieldValidator;

    public void testConfirmPassword() {
        SeleniumUser user = SeleniumUser.create();
        passwordTestHelper.setUpForPasswordTest(user);

        WebElement passwordField = indexPage.getRegistrationPasswordField();
        passwordField.sendKeys(user.getPassword());

        WebElement confirmPasswordField = indexPage.getRegistrationConfirmPasswordField();
        confirmPasswordField.sendKeys(SeleniumUser.createRandomPassword());

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
}
