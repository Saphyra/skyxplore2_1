package selenium.test.account.deleteaccount;

import org.openqa.selenium.WebDriver;

import lombok.Builder;
import selenium.logic.domain.SeleniumUser;
import selenium.logic.flow.Login;
import selenium.logic.page.AccountPage;
import selenium.logic.validator.NotificationValidator;
import selenium.test.account.deleteaccount.helper.DeleteAccountTestHelper;

@Builder
public class SuccessfulAccountDeletionTest {
    private static final String NOTIFICATION_ACCOUNT_DELETED = "Account törölve.";

    private final DeleteAccountTestHelper deleteAccountTestHelper;
    private final AccountPage accountPage;
    private final WebDriver driver;
    private final NotificationValidator notificationValidator;
    private final Login login;

    public void testSuccessfulAccountDeletion() {
        SeleniumUser user = deleteAccountTestHelper.registerAndNavigateToAccount();

        accountPage.getDeleteAccountPasswordField().sendKeys(user.getPassword());

        deleteAccountTestHelper.sendForm();
        driver.switchTo().alert().accept();

        notificationValidator.verifyOnlyOneNotification(NOTIFICATION_ACCOUNT_DELETED);

        login.loginFailure(user);
    }
}
