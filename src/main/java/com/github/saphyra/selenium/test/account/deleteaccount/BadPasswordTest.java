package com.github.saphyra.selenium.test.account.deleteaccount;

import static com.github.saphyra.selenium.logic.util.LocalizationUtil.getErrorCode;

import org.openqa.selenium.WebDriver;

import com.github.saphyra.selenium.logic.domain.SeleniumUser;
import com.github.saphyra.selenium.logic.page.AccountPage;
import com.github.saphyra.selenium.logic.validator.NotificationValidator;
import com.github.saphyra.selenium.test.account.deleteaccount.helper.DeleteAccountTestHelper;
import com.github.saphyra.skyxplore.common.ErrorCode;
import lombok.Builder;

@Builder
public class BadPasswordTest {
    private final DeleteAccountTestHelper deleteAccountTestHelper;
    private final AccountPage accountPage;
    private final WebDriver driver;
    private final NotificationValidator notificationValidator;

    public void testBadPassword() {
        deleteAccountTestHelper.registerAndNavigateToAccount();

        accountPage.getDeleteAccountPasswordField().sendKeys(SeleniumUser.createRandomPassword());

        deleteAccountTestHelper.sendForm();
        driver.switchTo().alert().accept();

        notificationValidator.verifyOnlyOneNotification(getErrorCode(driver, ErrorCode.WRONG_PASSWORD));
    }
}
