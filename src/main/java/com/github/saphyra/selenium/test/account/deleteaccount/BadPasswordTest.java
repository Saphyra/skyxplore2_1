package com.github.saphyra.selenium.test.account.deleteaccount;

import org.openqa.selenium.WebDriver;

import com.github.saphyra.selenium.test.account.deleteaccount.helper.DeleteAccountTestHelper;
import lombok.Builder;
import com.github.saphyra.selenium.logic.domain.SeleniumUser;
import com.github.saphyra.selenium.logic.page.AccountPage;
import com.github.saphyra.selenium.logic.validator.NotificationValidator;

@Builder
public class BadPasswordTest {
    private static final String MESSAGE_CODE_BAD_PASSWORD = "BAD_PASSWORD";

    private final DeleteAccountTestHelper deleteAccountTestHelper;
    private final AccountPage accountPage;
    private final WebDriver driver;
    private final NotificationValidator notificationValidator;
    private final MessageCodes messageCodes;

    public void testBadPassword() {
        deleteAccountTestHelper.registerAndNavigateToAccount();

        accountPage.getDeleteAccountPasswordField().sendKeys(SeleniumUser.createRandomPassword());

        deleteAccountTestHelper.sendForm();
        driver.switchTo().alert().accept();

        notificationValidator.verifyOnlyOneNotification(messageCodes.get(MESSAGE_CODE_BAD_PASSWORD));
    }
}
