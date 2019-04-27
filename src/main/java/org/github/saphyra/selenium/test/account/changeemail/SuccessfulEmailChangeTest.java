package org.github.saphyra.selenium.test.account.changeemail;

import lombok.Builder;
import org.openqa.selenium.WebElement;
import org.github.saphyra.selenium.logic.domain.MessageCodes;
import org.github.saphyra.selenium.logic.domain.SeleniumUser;
import org.github.saphyra.selenium.logic.page.AccountPage;
import org.github.saphyra.selenium.logic.validator.NotificationValidator;
import org.github.saphyra.selenium.test.account.changeemail.helper.ChangeEmailTestHelper;

import static org.junit.Assert.assertTrue;
import static org.github.saphyra.selenium.logic.util.Util.ATTRIBUTE_VALUE;

@Builder
public class SuccessfulEmailChangeTest {
    private static final String MESSAGE_CODE_EMAIL_CHANGE_SUCCESSFUL = "EMAIL_CHANGE_SUCCESSFUL";

    private final ChangeEmailTestHelper changeEmailTestHelper;
    private final AccountPage accountPage;
    private final NotificationValidator notificationValidator;
    private final MessageCodes messageCodes;

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
        notificationValidator.verifyOnlyOneNotification(messageCodes.get(MESSAGE_CODE_EMAIL_CHANGE_SUCCESSFUL));
        assertTrue(accountPage.getChangeEmailPasswordField().getAttribute(ATTRIBUTE_VALUE).isEmpty());
    }
}
