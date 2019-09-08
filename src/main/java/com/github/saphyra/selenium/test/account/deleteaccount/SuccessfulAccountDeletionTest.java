package com.github.saphyra.selenium.test.account.deleteaccount;

import static com.github.saphyra.selenium.logic.util.LocalizationUtil.getAdditionalContent;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.github.saphyra.selenium.logic.domain.SeleniumUser;
import com.github.saphyra.selenium.logic.domain.localization.Page;
import com.github.saphyra.selenium.logic.flow.Login;
import com.github.saphyra.selenium.logic.page.AccountPage;
import com.github.saphyra.selenium.logic.validator.NotificationValidator;
import com.github.saphyra.selenium.test.account.deleteaccount.helper.DeleteAccountTestHelper;
import lombok.Builder;

@Builder
public class SuccessfulAccountDeletionTest {
    private static final String MESSAGE_CODE_ACCOUNT_DELETED = "account-deleted";

    private final DeleteAccountTestHelper deleteAccountTestHelper;
    private final AccountPage accountPage;
    private final WebDriver driver;
    private final NotificationValidator notificationValidator;
    private final Login login;

    public void testSuccessfulAccountDeletion() {
        SeleniumUser user = deleteAccountTestHelper.registerAndNavigateToAccount();

        accountPage.getDeleteAccountPasswordField().sendKeys(user.getPassword());

        deleteAccountTestHelper.sendForm();
        new WebDriverWait(driver, 2).until(ExpectedConditions.alertIsPresent());
        driver.switchTo().alert().accept();

        notificationValidator.verifyOnlyOneNotification(getAdditionalContent(driver, Page.INDEX, MESSAGE_CODE_ACCOUNT_DELETED));

        login.loginFailure(user);
    }
}
