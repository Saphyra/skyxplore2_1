package selenium.test.account.changepassword;

import lombok.Builder;
import org.openqa.selenium.WebElement;
import selenium.logic.domain.SeleniumUser;
import selenium.logic.flow.Login;
import selenium.logic.flow.Logout;
import selenium.logic.flow.Navigate;
import selenium.logic.page.AccountPage;
import selenium.logic.validator.NotificationValidator;
import selenium.test.account.changepassword.helper.ChangePasswordTestHelper;

@Builder
public class SuccessfulPasswordChangeTest {
    private static final String NOTIFICATION_SUCCESSFUL_PASSWORD_CHANGE = "Jelszó megváltoztatása sikeres.";

    private final ChangePasswordTestHelper changePasswordTestHelper;
    private final AccountPage accountPage;
    private final Logout logout;
    private final Login login;
    private final Navigate navigate;
    private final NotificationValidator notificationValidator;

    public void testSuccessfulPasswordChange() {
        SeleniumUser user = changePasswordTestHelper.registerAndNavigateToAccount();
        SeleniumUser changed = user.cloneUser();
        changed.setPassword(SeleniumUser.createRandomPassword());

        WebElement newPasswordField = accountPage.getNewPasswordField();
        newPasswordField.sendKeys(changed.getPassword());

        WebElement confirmPasswordField = accountPage.getNewConfirmPasswordField();
        confirmPasswordField.sendKeys(changed.getPassword());

        WebElement currentPasswordField = accountPage.getCurrentNewPasswordField();
        currentPasswordField.sendKeys(user.getPassword());

        changePasswordTestHelper.sendForm();

        verifyPasswordChange(user, changed);
    }

    private void verifyPasswordChange(SeleniumUser originalUser, SeleniumUser changed) {
        verifySuccessfulNotification();

        changePasswordTestHelper.verifyInputFieldsAreEmpty();

        logout.logOut();
        login.loginFailure(originalUser);

        login.login(changed);

        navigate.toAccountPage();

        accountPage.getNewPasswordField().sendKeys(originalUser.getPassword());
        accountPage.getNewConfirmPasswordField().sendKeys(originalUser.getPassword());
        accountPage.getCurrentNewPasswordField().sendKeys(changed.getPassword());

        changePasswordTestHelper.sendForm();

        verifySuccessfulNotification();
    }

    private void verifySuccessfulNotification() {
        notificationValidator.verifyOnlyOneNotification(NOTIFICATION_SUCCESSFUL_PASSWORD_CHANGE);
    }
}
