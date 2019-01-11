package selenium.test.account.changepassword;

import lombok.Builder;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import selenium.logic.domain.SeleniumUser;
import selenium.logic.flow.Navigate;
import selenium.logic.page.AccountPage;
import selenium.logic.validator.FieldValidator;
import selenium.test.account.changepassword.helper.ChangePasswordTestHelper;

import static selenium.logic.util.Util.crop;
import static skyxplore.controller.request.user.UserRegistrationRequest.PASSWORD_MIN_LENGTH;

@Builder
public class TooShortPasswordTest {
    private static final String ERROR_MESSAGE_PASSWORD_TOO_SHORT = "Új jelszó túl rövid! (Minimum 6 karakter)";

    private final WebDriver driver;
    private final AccountPage accountPage;
    private final FieldValidator fieldValidator;
    private final Navigate navigate;
    private final ChangePasswordTestHelper changePasswordTestHelper;

    public void testTooShortPassword() {
        SeleniumUser user = changePasswordTestHelper.setUpWithCurrentPassword();

        WebElement newPasswordField = accountPage.getNewPasswordField();

        newPasswordField.sendKeys(crop(user.getPassword(), PASSWORD_MIN_LENGTH - 1));

        fieldValidator.verifyError(
            accountPage.getInvalidNewPasswordField(),
            ERROR_MESSAGE_PASSWORD_TOO_SHORT,
            newPasswordField,
            accountPage.getChangePasswordButton(),
            accountPage.getInvalidNewConfirmPasswordField(),
            accountPage.getCurrentInvalidNewPasswordField()
        );
    }
}
