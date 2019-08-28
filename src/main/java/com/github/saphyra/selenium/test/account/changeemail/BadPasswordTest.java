package com.github.saphyra.selenium.test.account.changeemail;

import lombok.Builder;
import org.openqa.selenium.WebElement;

import com.github.saphyra.selenium.logic.domain.SeleniumUser;
import com.github.saphyra.selenium.logic.page.AccountPage;
import com.github.saphyra.selenium.logic.validator.FieldValidator;
import com.github.saphyra.selenium.logic.validator.NotificationValidator;
import com.github.saphyra.selenium.test.account.changeemail.helper.ChangeEmailTestHelper;

import static org.junit.Assert.assertTrue;
import static com.github.saphyra.selenium.logic.util.Util.ATTRIBUTE_VALUE;

@Builder
public class BadPasswordTest {
    private static final String MESSAGE_CODE_BAD_PASSWORD = "BAD_PASSWORD";

    private final ChangeEmailTestHelper changeEmailTestHelper;
    private final AccountPage accountPage;
    private final FieldValidator fieldValidator;
    private final NotificationValidator notificationValidator;
    private final MessageCodes messageCodes;

    public void testBadPassword() {
        changeEmailTestHelper.setUpWithRandomEmail();

        WebElement passwordField = accountPage.getChangeEmailPasswordField();
        passwordField.sendKeys(SeleniumUser.createRandomPassword());

        changeEmailTestHelper.submitForm();

        verifyFailure();
    }

    private void verifyFailure() {
        notificationValidator.verifyOnlyOneNotification(messageCodes.get(MESSAGE_CODE_BAD_PASSWORD));
        assertTrue(accountPage.getChangeEmailPasswordField().getAttribute(ATTRIBUTE_VALUE).isEmpty());
    }
}
