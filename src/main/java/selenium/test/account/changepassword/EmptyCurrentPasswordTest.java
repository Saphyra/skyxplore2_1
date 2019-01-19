package selenium.test.account.changepassword;

import lombok.Builder;
import selenium.logic.domain.SeleniumUser;
import selenium.logic.page.AccountPage;
import selenium.logic.validator.FieldValidator;
import selenium.test.account.changepassword.helper.ChangePasswordTestHelper;

@Builder
public class EmptyCurrentPasswordTest {
    private static final String ERROR_MESSAGE_EMPTY_CURRENT_PASSWORD = "Jelszó megadása kötelező!";

    private final ChangePasswordTestHelper changePasswordTestHelper;
    private final AccountPage accountPage;
    private final FieldValidator fieldValidator;

    public void testEmptyCurrentPassword() {
        SeleniumUser user = changePasswordTestHelper.registerAndNavigateToAccount();

        accountPage.getNewPasswordField().sendKeys(user.getPassword());
        accountPage.getNewConfirmPasswordField().sendKeys(user.getPassword());

        fieldValidator.verifyError(
            accountPage.getCurrentInvalidNewPasswordField(),
            ERROR_MESSAGE_EMPTY_CURRENT_PASSWORD,
            accountPage.getCurrentNewPasswordField(),
            accountPage.getChangePasswordButton(),
            accountPage.getInvalidNewPasswordField(),
            accountPage.getInvalidNewConfirmPasswordField()
        );
    }
}
