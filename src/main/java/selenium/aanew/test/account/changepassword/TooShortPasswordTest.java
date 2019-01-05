package selenium.aanew.test.account.changepassword;

import lombok.Builder;
import lombok.NonNull;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import selenium.aanew.logic.domain.SeleniumUser;
import selenium.aanew.logic.flow.Navigate;
import selenium.aanew.logic.flow.Registration;
import selenium.aanew.logic.validator.FieldValidator;
import selenium.page.AccountPage;

import static selenium.aanew.logic.util.DOMUtil.cleanNotifications;
import static selenium.aanew.logic.util.StringUtil.crop;
import static skyxplore.controller.request.user.UserRegistrationRequest.PASSWORD_MIN_LENGTH;

@Builder
public class TooShortPasswordTest {
    private static final String ERROR_MESSAGE_PASSWORD_TOO_SHORT = "Új jelszó túl rövid! (Minimum 6 karakter)";

    @NonNull
    private final WebDriver driver;
    @NonNull
    private final AccountPage accountPage;
    @NonNull
    private final Registration registration;
    @NonNull
    private final FieldValidator fieldValidator;
    @NonNull
    private final Navigate navigate;

    public void validateTooShortPassword() {
        SeleniumUser user = registration.registerUser();
        navigate.toAccountPage();
        setUpForChangePasswordTest(user);

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

    private void setUpForChangePasswordTest(SeleniumUser user) {
        clearAll();

        accountPage.getCurrentNewPasswordField().sendKeys(user.getPassword());
    }

    private void clearAll() {
        cleanNotifications(driver);

        accountPage.getNewPasswordField().clear();
        accountPage.getNewConfirmPasswordField().clear();
        accountPage.getCurrentNewPasswordField().clear();
    }
}
