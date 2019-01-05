package selenium.aanew.test.account.changepassword;

import lombok.Builder;
import org.openqa.selenium.WebElement;
import selenium.aanew.logic.domain.SeleniumUser;
import selenium.aanew.logic.page.AccountPage;
import selenium.aanew.logic.validator.FieldValidator;
import selenium.aanew.test.account.changepassword.helper.ChangePasswordTestHelper;

@Builder
public class BadConfirmPasswordTest {
    private static final String ERROR_MESSAGE_BAD_CONFIRM_PASSWORD = "A jelszavak nem egyeznek.";

    private final ChangePasswordTestHelper changePasswordTestHelper;
    private final AccountPage accountPage;
    private final FieldValidator fieldValidator;

    public void testBadConfirmPassword() {
        changePasswordTestHelper.setUpWithCurrentPassword();

        WebElement newPasswordField = accountPage.getNewPasswordField();
        newPasswordField.sendKeys(SeleniumUser.createRandomPassword());

        WebElement newConfirmPasswordField = accountPage.getNewConfirmPasswordField();
        newConfirmPasswordField.sendKeys(SeleniumUser.createRandomPassword());

        fieldValidator.verifyError(
            accountPage.getInvalidNewPasswordField(),
            ERROR_MESSAGE_BAD_CONFIRM_PASSWORD,
            newPasswordField,
            accountPage.getChangePasswordButton(),
            accountPage.getCurrentInvalidNewPasswordField()
        );

        fieldValidator.verifyError(
            accountPage.getInvalidNewConfirmPasswordField(),
            ERROR_MESSAGE_BAD_CONFIRM_PASSWORD,
            newConfirmPasswordField,
            accountPage.getChangePasswordButton()
        );
    }
}
