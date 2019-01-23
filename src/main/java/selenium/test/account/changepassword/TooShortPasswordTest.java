package selenium.test.account.changepassword;

import lombok.Builder;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import selenium.logic.domain.MessageCodes;
import selenium.logic.domain.SeleniumUser;
import selenium.logic.flow.Navigate;
import selenium.logic.page.AccountPage;
import selenium.logic.validator.FieldValidator;
import selenium.test.account.changepassword.helper.ChangePasswordTestHelper;

import static selenium.logic.util.Util.crop;
import static skyxplore.controller.request.user.UserRegistrationRequest.PASSWORD_MIN_LENGTH;

@Builder
public class TooShortPasswordTest {
    private static final String MESSAGE_CODE_PASSWORD_TOO_SHORT = "NEW_PASSWORD_TOO_SHORT";

    private final WebDriver driver;
    private final AccountPage accountPage;
    private final FieldValidator fieldValidator;
    private final Navigate navigate;
    private final ChangePasswordTestHelper changePasswordTestHelper;
    private final MessageCodes messageCodes;

    public void testTooShortPassword() {
        SeleniumUser user = changePasswordTestHelper.setUpWithCurrentPassword();

        WebElement newPasswordField = accountPage.getNewPasswordField();

        newPasswordField.sendKeys(crop(user.getPassword(), PASSWORD_MIN_LENGTH - 1));

        fieldValidator.verifyError(
            accountPage.getInvalidNewPasswordField(),
            messageCodes.get(MESSAGE_CODE_PASSWORD_TOO_SHORT),
            newPasswordField,
            accountPage.getChangePasswordButton(),
            accountPage.getInvalidNewConfirmPasswordField(),
            accountPage.getCurrentInvalidNewPasswordField()
        );
    }
}
