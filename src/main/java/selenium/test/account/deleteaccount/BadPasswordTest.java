package selenium.test.account.deleteaccount;

import org.openqa.selenium.WebDriver;

import lombok.Builder;
import selenium.logic.domain.SeleniumUser;
import selenium.logic.page.AccountPage;
import selenium.logic.validator.NotificationValidator;
import selenium.test.account.deleteaccount.helper.DeleteAccountTestHelper;

@Builder
public class BadPasswordTest {
    private static final String NOTIFICATION_BAD_PASSWORD = "Hibás jelszó.";

    private final DeleteAccountTestHelper deleteAccountTestHelper;
    private final AccountPage accountPage;
    private final WebDriver driver;
    private final NotificationValidator notificationValidator;

    public void testBadPassword() {
        deleteAccountTestHelper.registerAndNavigateToAccount();

        accountPage.getDeleteAccountPasswordField().sendKeys(SeleniumUser.createRandomPassword());

        deleteAccountTestHelper.sendForm();
        driver.switchTo().alert().accept();

        notificationValidator.verifyOnlyOneNotification(NOTIFICATION_BAD_PASSWORD);
    }
}
