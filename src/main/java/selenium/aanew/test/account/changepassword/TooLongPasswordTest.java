package selenium.aanew.test.account.changepassword;

import lombok.Builder;
import org.openqa.selenium.WebElement;
import selenium.aanew.logic.flow.Navigate;
import selenium.aanew.logic.flow.Registration;
import selenium.aanew.logic.page.AccountPage;
import selenium.aanew.logic.validator.FieldValidator;
import selenium.aanew.test.account.changepassword.helper.ChangePasswordTestSetup;

import static skyxplore.controller.request.user.UserRegistrationRequest.PASSWORD_MAX_LENGTH;

@Builder
public class TooLongPasswordTest {
    private static final String TOO_LONG_PASSWORD;

    static {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i <= PASSWORD_MAX_LENGTH + 1; i++) {
            builder.append("a");
        }
        TOO_LONG_PASSWORD = builder.toString();
    }

    private static final String ERROR_MESSAGE_PASSWORD_TOO_LONG = "Új jelszó túl hosszú! (Maximum 30 karakter)";

    private final ChangePasswordTestSetup changePasswordTestSetup;
    private final Registration registration;
    private final Navigate navigate;
    private final AccountPage accountPage;
    private final FieldValidator fieldValidator;

    public void validateTooLongPassword() {
        changePasswordTestSetup.setUpForChangePasswordTest();

        WebElement newPasswordField = accountPage.getNewPasswordField();
        newPasswordField.sendKeys(TOO_LONG_PASSWORD);

        fieldValidator.verifyError(
            accountPage.getInvalidNewPasswordField(),
            ERROR_MESSAGE_PASSWORD_TOO_LONG,
            newPasswordField,
            accountPage.getChangePasswordButton(),
            accountPage.getInvalidNewConfirmPasswordField(),
            accountPage.getCurrentInvalidNewPasswordField()
        );
    }
}
