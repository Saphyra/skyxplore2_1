package selenium.aanew.test.account.changepassword;

import lombok.Builder;
import org.openqa.selenium.WebElement;
import selenium.aanew.logic.domain.SeleniumUser;
import selenium.aanew.logic.page.AccountPage;
import selenium.aanew.logic.validator.NotificationValidator;
import selenium.aanew.test.account.changepassword.helper.ChangePasswordTestHelper;

import static org.junit.Assert.assertTrue;
import static selenium.aanew.logic.util.DOMUtil.ATTRIBUTE_VALUE;

@Builder
public class BadPasswordTest {
    private static final String NOTIFICATION_BAD_PASSWORD = "Hibás jelszó.";

    private final ChangePasswordTestHelper changePasswordTestHelper;
    private final AccountPage accountPage;
    private final NotificationValidator notificationValidator;

    public void testBadPassword() {
        SeleniumUser user = changePasswordTestHelper.registerAndNavigateToAccount();

        String newPassword = SeleniumUser.createRandomPassword();

        WebElement newPasswordField = accountPage.getNewPasswordField();
        newPasswordField.sendKeys(newPassword);

        WebElement confirmPasswordField = accountPage.getNewConfirmPasswordField();
        confirmPasswordField.sendKeys(newPassword);

        WebElement currentPasswordField = accountPage.getCurrentNewPasswordField();
        currentPasswordField.sendKeys(SeleniumUser.createRandomPassword());

        changePasswordTestHelper.sendForm();

        notificationValidator.verifyOnlyOneNotification(NOTIFICATION_BAD_PASSWORD);

        assertTrue(accountPage.getNewPasswordField().getAttribute(ATTRIBUTE_VALUE).isEmpty());
        assertTrue(accountPage.getNewConfirmPasswordField().getAttribute(ATTRIBUTE_VALUE).isEmpty());
        assertTrue(accountPage.getCurrentNewPasswordField().getAttribute(ATTRIBUTE_VALUE).isEmpty());
    }
}
