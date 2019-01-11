package selenium.test.account.changepassword;

import lombok.Builder;
import org.openqa.selenium.WebElement;
import selenium.logic.domain.SeleniumUser;
import selenium.logic.page.AccountPage;
import selenium.logic.validator.NotificationValidator;
import selenium.test.account.changepassword.helper.ChangePasswordTestHelper;

@Builder
public class BadPasswordTest {
    private static final String NOTIFICATION_BAD_PASSWORD = "Hibás jelszó.";

    private final ChangePasswordTestHelper changePasswordTestHelper;
    private final AccountPage accountPage;
    private final NotificationValidator notificationValidator;

    public void testBadPassword() {
        changePasswordTestHelper.registerAndNavigateToAccount();

        String newPassword = SeleniumUser.createRandomPassword();

        WebElement newPasswordField = accountPage.getNewPasswordField();
        newPasswordField.sendKeys(newPassword);

        WebElement confirmPasswordField = accountPage.getNewConfirmPasswordField();
        confirmPasswordField.sendKeys(newPassword);

        WebElement currentPasswordField = accountPage.getCurrentNewPasswordField();
        currentPasswordField.sendKeys(SeleniumUser.createRandomPassword());

        changePasswordTestHelper.sendForm();

        notificationValidator.verifyOnlyOneNotification(NOTIFICATION_BAD_PASSWORD);

        changePasswordTestHelper.verifyInputFieldsAreEmpty();
    }
}
