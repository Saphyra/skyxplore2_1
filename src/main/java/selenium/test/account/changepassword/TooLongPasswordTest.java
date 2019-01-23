package selenium.test.account.changepassword;

import lombok.Builder;
import org.openqa.selenium.WebElement;
import selenium.logic.domain.MessageCodes;
import selenium.logic.flow.Navigate;
import selenium.logic.flow.Registration;
import selenium.logic.page.AccountPage;
import selenium.logic.validator.FieldValidator;
import selenium.test.account.changepassword.helper.ChangePasswordTestHelper;

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

    private static final String MESSAGE_CODE_PASSWORD_TOO_LONG = "NEW_PASSWORD_TOO_LONG";

    private final ChangePasswordTestHelper changePasswordTestHelper;
    private final Registration registration;
    private final Navigate navigate;
    private final AccountPage accountPage;
    private final FieldValidator fieldValidator;
    private final MessageCodes messageCodes;

    public void testTooLongPassword() {
        changePasswordTestHelper.setUpWithCurrentPassword();

        WebElement newPasswordField = accountPage.getNewPasswordField();
        newPasswordField.sendKeys(TOO_LONG_PASSWORD);

        fieldValidator.verifyError(
            accountPage.getInvalidNewPasswordField(),
            messageCodes.get(MESSAGE_CODE_PASSWORD_TOO_LONG),
            newPasswordField,
            accountPage.getChangePasswordButton(),
            accountPage.getInvalidNewConfirmPasswordField(),
            accountPage.getCurrentInvalidNewPasswordField()
        );
    }
}
