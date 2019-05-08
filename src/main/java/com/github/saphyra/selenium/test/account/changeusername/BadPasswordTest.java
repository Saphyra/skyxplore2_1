package com.github.saphyra.selenium.test.account.changeusername;

import com.github.saphyra.selenium.test.account.changeusername.helper.ChangeUserNameTestHelper;
import lombok.Builder;
import com.github.saphyra.selenium.logic.domain.localization.MessageCodes;
import com.github.saphyra.selenium.logic.domain.SeleniumUser;
import com.github.saphyra.selenium.logic.page.AccountPage;
import com.github.saphyra.selenium.logic.validator.NotificationValidator;

import static org.junit.Assert.assertTrue;
import static com.github.saphyra.selenium.logic.util.Util.ATTRIBUTE_VALUE;

@Builder
public class BadPasswordTest {
    private static final String MESSAGE_CODE_BAD_PASSWORD = "BAD_PASSWORD";

    private final ChangeUserNameTestHelper changeUserNameTestHelper;
    private final AccountPage accountPage;
    private final NotificationValidator notificationValidator;
    private final MessageCodes messageCodes;

    public void testBadPassword() {
        changeUserNameTestHelper.setUpWithRandomUserName();

        accountPage.getChangeUserNamePasswordField().sendKeys(SeleniumUser.createRandomPassword());
        changeUserNameTestHelper.sendForm();

        notificationValidator.verifyOnlyOneNotification(messageCodes.get(MESSAGE_CODE_BAD_PASSWORD));
        assertTrue(accountPage.getChangeUserNamePasswordField().getAttribute(ATTRIBUTE_VALUE).isEmpty());

        assertTrue(accountPage.getChangeUserNamePasswordField().getText().isEmpty());
    }
}
