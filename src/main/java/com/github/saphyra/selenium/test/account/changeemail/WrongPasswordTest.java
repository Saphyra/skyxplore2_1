package com.github.saphyra.selenium.test.account.changeemail;

import com.github.saphyra.selenium.logic.domain.SeleniumUser;
import com.github.saphyra.selenium.logic.page.AccountPage;
import com.github.saphyra.selenium.logic.validator.FieldValidator;
import com.github.saphyra.selenium.logic.validator.NotificationValidator;
import com.github.saphyra.selenium.test.account.changeemail.helper.ChangeEmailTestHelper;
import com.github.saphyra.skyxplore.common.ErrorCode;
import lombok.Builder;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import static com.github.saphyra.selenium.logic.util.LocalizationUtil.getErrorCode;
import static com.github.saphyra.selenium.logic.util.Util.ATTRIBUTE_VALUE;
import static org.junit.Assert.assertTrue;

@Builder
public class WrongPasswordTest {
    private final WebDriver driver;
    private final ChangeEmailTestHelper changeEmailTestHelper;
    private final AccountPage accountPage;
    private final FieldValidator fieldValidator;
    private final NotificationValidator notificationValidator;

    public void testBadPassword() {
        changeEmailTestHelper.setUpWithRandomEmail();

        WebElement passwordField = accountPage.getChangeEmailPasswordField();
        passwordField.sendKeys(SeleniumUser.createRandomPassword());

        changeEmailTestHelper.submitForm();

        verifyFailure();
    }

    private void verifyFailure() {
        notificationValidator.verifyOnlyOneNotification(getErrorCode(driver, ErrorCode.WRONG_PASSWORD));
        assertTrue(accountPage.getChangeEmailPasswordField().getAttribute(ATTRIBUTE_VALUE).isEmpty());
    }
}
