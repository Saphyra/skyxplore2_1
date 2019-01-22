package selenium.test.registration.password;

import org.openqa.selenium.WebElement;

import lombok.Builder;
import selenium.logic.domain.MessageCodes;
import selenium.logic.domain.SeleniumUser;
import selenium.logic.page.IndexPage;
import selenium.logic.validator.FieldValidator;
import selenium.test.registration.password.helper.PasswordTestHelper;

@Builder
public class ConfirmPasswordTest {
    private static final String MESSAGE_CODE_BAD_CONFIRM_PASSWORD = "BAD_CONFIRM_PASSWORD";

    private final PasswordTestHelper passwordTestHelper;
    private final IndexPage indexPage;
    private final FieldValidator fieldValidator;
    private final MessageCodes messageCodes;

    public void testConfirmPassword() {
        SeleniumUser user = SeleniumUser.create();
        passwordTestHelper.setUpForPasswordTest(user);

        WebElement passwordField = indexPage.getRegistrationPasswordField();
        passwordField.sendKeys(user.getPassword());

        WebElement confirmPasswordField = indexPage.getRegistrationConfirmPasswordField();
        confirmPasswordField.sendKeys(SeleniumUser.createRandomPassword());

        fieldValidator.verifyError(
            indexPage.getInvalidPasswordField(),
            messageCodes.get(MESSAGE_CODE_BAD_CONFIRM_PASSWORD),
            passwordField,
            indexPage.getRegisterButton()
        );

        fieldValidator.verifyError(
            indexPage.getInvalidConfirmPasswordField(),
            messageCodes.get(MESSAGE_CODE_BAD_CONFIRM_PASSWORD),
            confirmPasswordField,
            indexPage.getRegisterButton()
        );
    }
}
