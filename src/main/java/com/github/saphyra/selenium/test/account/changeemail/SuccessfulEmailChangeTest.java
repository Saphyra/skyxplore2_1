package com.github.saphyra.selenium.test.account.changeemail;

import static com.github.saphyra.selenium.logic.util.LocalizationUtil.getAdditionalContent;
import static com.github.saphyra.selenium.logic.util.Util.ATTRIBUTE_VALUE;
import static org.junit.Assert.assertTrue;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.github.saphyra.selenium.logic.domain.SeleniumUser;
import com.github.saphyra.selenium.logic.domain.localization.Page;
import com.github.saphyra.selenium.logic.page.AccountPage;
import com.github.saphyra.selenium.logic.validator.NotificationValidator;
import com.github.saphyra.selenium.test.account.changeemail.helper.ChangeEmailTestHelper;
import lombok.Builder;

@Builder
public class SuccessfulEmailChangeTest {
    private static final String MESSAGE_CODE_EMAIL_CHANGED = "email-changed";

    private final WebDriver driver;
    private final ChangeEmailTestHelper changeEmailTestHelper;
    private final AccountPage accountPage;
    private final NotificationValidator notificationValidator;

    public void testSuccessfulEmailChange() {
        SeleniumUser user = changeEmailTestHelper.registerAndNavigateToAccount();

        fillInputFields(user);

        changeEmailTestHelper.submitForm();

        verifySuccess();
    }

    private void fillInputFields(SeleniumUser user) {
        WebElement emailField = accountPage.getChangeEmailField();
        emailField.sendKeys(SeleniumUser.createRandomEmail());

        WebElement passwordField = accountPage.getChangeEmailPasswordField();
        passwordField.sendKeys(user.getPassword());
    }

    private void verifySuccess() {
        notificationValidator.verifyOnlyOneNotification(getAdditionalContent(driver, Page.ACCOUNT, MESSAGE_CODE_EMAIL_CHANGED));
        assertTrue(accountPage.getChangeEmailPasswordField().getAttribute(ATTRIBUTE_VALUE).isEmpty());
    }
}
