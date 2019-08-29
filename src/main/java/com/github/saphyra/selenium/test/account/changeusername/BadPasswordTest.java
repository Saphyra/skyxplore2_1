package com.github.saphyra.selenium.test.account.changeusername;

import com.github.saphyra.selenium.logic.domain.SeleniumUser;
import com.github.saphyra.selenium.logic.page.AccountPage;
import com.github.saphyra.selenium.logic.validator.NotificationValidator;
import com.github.saphyra.selenium.test.account.changeusername.helper.ChangeUserNameTestHelper;
import com.github.saphyra.skyxplore.common.ErrorCode;
import lombok.Builder;
import org.openqa.selenium.WebDriver;

import static com.github.saphyra.selenium.logic.util.LocalizationUtil.getErrorCode;
import static com.github.saphyra.selenium.logic.util.Util.ATTRIBUTE_VALUE;
import static org.junit.Assert.assertTrue;

@Builder
public class BadPasswordTest {
    private final WebDriver driver;
    private final ChangeUserNameTestHelper changeUserNameTestHelper;
    private final AccountPage accountPage;
    private final NotificationValidator notificationValidator;

    public void testBadPassword() {
        changeUserNameTestHelper.setUpWithRandomUserName();

        accountPage.getChangeUserNamePasswordField().sendKeys(SeleniumUser.createRandomPassword());
        changeUserNameTestHelper.sendForm();

        notificationValidator.verifyOnlyOneNotification(getErrorCode(driver, ErrorCode.WRONG_PASSWORD));
        assertTrue(accountPage.getChangeUserNamePasswordField().getAttribute(ATTRIBUTE_VALUE).isEmpty());

        assertTrue(accountPage.getChangeUserNamePasswordField().getText().isEmpty());
    }
}
