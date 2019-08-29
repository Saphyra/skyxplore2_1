package com.github.saphyra.selenium.test.account.changeusername;

import com.github.saphyra.selenium.logic.domain.SeleniumUser;
import com.github.saphyra.selenium.logic.domain.localization.Page;
import com.github.saphyra.selenium.logic.flow.Login;
import com.github.saphyra.selenium.logic.flow.Logout;
import com.github.saphyra.selenium.logic.page.AccountPage;
import com.github.saphyra.selenium.logic.validator.NotificationValidator;
import com.github.saphyra.selenium.test.account.changeusername.helper.ChangeUserNameTestHelper;
import lombok.Builder;
import org.openqa.selenium.WebDriver;

import static com.github.saphyra.selenium.logic.util.LocalizationUtil.getAdditionalContent;
import static org.junit.Assert.assertTrue;

@Builder
public class SuccessfulUserNameChangeTest {
    private static final String MESSAGE_CODE_USERNAME_CHANGED = "username-changed";

    private final WebDriver driver;
    private final ChangeUserNameTestHelper changeUserNameTestHelper;
    private final AccountPage accountPage;
    private final NotificationValidator notificationValidator;
    private final Logout logout;
    private final Login login;

    public void testSuccessfulUserNameChange() {
        SeleniumUser user = changeUserNameTestHelper.setUpWithCurrentPassword();

        String newName = SeleniumUser.createRandomUserName();
        SeleniumUser newUser = user.cloneUser();
        newUser.setUserName(newName);

        accountPage.getChangeUserNameField().sendKeys(newName);

        changeUserNameTestHelper.sendForm();

        notificationValidator.verifyOnlyOneNotification(getAdditionalContent(driver, Page.ACCOUNT, MESSAGE_CODE_USERNAME_CHANGED));
        assertTrue(accountPage.getChangeUserNamePasswordField().getText().isEmpty());

        logout.logOut();
        login.loginFailure(user);
        login.login(newUser);

    }
}
