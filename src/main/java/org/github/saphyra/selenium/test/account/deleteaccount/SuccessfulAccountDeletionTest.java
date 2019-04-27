package org.github.saphyra.selenium.test.account.deleteaccount;

import org.openqa.selenium.WebDriver;

import lombok.Builder;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.github.saphyra.selenium.logic.domain.MessageCodes;
import org.github.saphyra.selenium.logic.domain.SeleniumUser;
import org.github.saphyra.selenium.logic.flow.Login;
import org.github.saphyra.selenium.logic.page.AccountPage;
import org.github.saphyra.selenium.logic.validator.NotificationValidator;
import org.github.saphyra.selenium.test.account.deleteaccount.helper.DeleteAccountTestHelper;

@Builder
public class SuccessfulAccountDeletionTest {
    private static final String MESSAGE_CODE_ACCOUNT_DELETED = "ACCOUNT_DELETION_SUCCESSFUL";

    private final DeleteAccountTestHelper deleteAccountTestHelper;
    private final AccountPage accountPage;
    private final WebDriver driver;
    private final NotificationValidator notificationValidator;
    private final Login login;
    private final MessageCodes messageCodes;

    public void testSuccessfulAccountDeletion() {
        SeleniumUser user = deleteAccountTestHelper.registerAndNavigateToAccount();

        accountPage.getDeleteAccountPasswordField().sendKeys(user.getPassword());

        deleteAccountTestHelper.sendForm();
        new WebDriverWait(driver, 2).until(ExpectedConditions.alertIsPresent());
        driver.switchTo().alert().accept();

        notificationValidator.verifyOnlyOneNotification(messageCodes.get(MESSAGE_CODE_ACCOUNT_DELETED));

        login.loginFailure(user);
    }
}
