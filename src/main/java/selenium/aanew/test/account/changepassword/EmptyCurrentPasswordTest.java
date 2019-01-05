package selenium.aanew.test.account.changepassword;

import lombok.Builder;
import selenium.aanew.logic.domain.SeleniumUser;
import selenium.aanew.logic.page.AccountPage;
import selenium.aanew.logic.validator.FieldValidator;
import selenium.aanew.test.account.changepassword.helper.ChangePasswordTestSetup;

@Builder
public class EmptyCurrentPasswordTest {
    private static final String ERROR_MESSAGE_EMPTY_CURRENT_PASSWORD = "Jelszó megadása kötelező!";

    private final ChangePasswordTestSetup changePasswordTestSetup;
    private final AccountPage accountPage;
    private final FieldValidator fieldValidator;

    public void validateEmptyCurrentPassword() {
        SeleniumUser user = changePasswordTestSetup.registerAndNavigateToAccount();

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
