package selenium.test.account.changepassword;

import lombok.Builder;
import selenium.logic.domain.MessageCodes;
import selenium.logic.domain.SeleniumUser;
import selenium.logic.page.AccountPage;
import selenium.logic.validator.FieldValidator;
import selenium.test.account.changepassword.helper.ChangePasswordTestHelper;

@Builder
public class EmptyCurrentPasswordTest {
    private static final String MESSAGE_CODE_EMPTY_CURRENT_PASSWORD = "CURRENT_PASSWORD_IS_EMPTY";

    private final ChangePasswordTestHelper changePasswordTestHelper;
    private final AccountPage accountPage;
    private final FieldValidator fieldValidator;
    private final MessageCodes messageCodes;

    public void testEmptyCurrentPassword() {
        SeleniumUser user = changePasswordTestHelper.registerAndNavigateToAccount();

        accountPage.getNewPasswordField().sendKeys(user.getPassword());
        accountPage.getNewConfirmPasswordField().sendKeys(user.getPassword());

        fieldValidator.verifyError(
            accountPage.getCurrentInvalidNewPasswordField(),
            messageCodes.get(MESSAGE_CODE_EMPTY_CURRENT_PASSWORD),
            accountPage.getCurrentNewPasswordField(),
            accountPage.getChangePasswordButton(),
            accountPage.getInvalidNewPasswordField(),
            accountPage.getInvalidNewConfirmPasswordField()
        );
    }
}
