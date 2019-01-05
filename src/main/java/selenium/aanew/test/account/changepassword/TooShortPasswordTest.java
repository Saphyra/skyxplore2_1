package selenium.aanew.test.account.changepassword;

import lombok.Builder;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import selenium.aanew.logic.domain.SeleniumUser;
import selenium.aanew.logic.flow.Navigate;
import selenium.aanew.logic.flow.Registration;
import selenium.aanew.logic.page.AccountPage;
import selenium.aanew.logic.validator.FieldValidator;
import selenium.aanew.test.account.changepassword.helper.ChangePasswordTestSetup;

import static selenium.aanew.logic.util.StringUtil.crop;
import static skyxplore.controller.request.user.UserRegistrationRequest.PASSWORD_MIN_LENGTH;

@Builder
public class TooShortPasswordTest {
    private static final String ERROR_MESSAGE_PASSWORD_TOO_SHORT = "Új jelszó túl rövid! (Minimum 6 karakter)";

    private final WebDriver driver;
    private final AccountPage accountPage;
    private final Registration registration;
    private final FieldValidator fieldValidator;
    private final Navigate navigate;
    private final ChangePasswordTestSetup changePasswordTestSetup;

    public void validateTooShortPassword() {
        SeleniumUser user = registration.registerUser();
        navigate.toAccountPage();
        changePasswordTestSetup.setUpForChangePasswordTest(user);

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
