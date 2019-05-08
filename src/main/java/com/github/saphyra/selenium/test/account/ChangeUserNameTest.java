package com.github.saphyra.selenium.test.account;

import org.junit.Test;
import com.github.saphyra.selenium.SeleniumTestApplication;
import com.github.saphyra.selenium.logic.flow.Login;
import com.github.saphyra.selenium.logic.flow.Logout;
import com.github.saphyra.selenium.logic.flow.Navigate;
import com.github.saphyra.selenium.logic.flow.Registration;
import com.github.saphyra.selenium.logic.page.AccountPage;
import com.github.saphyra.selenium.logic.validator.FieldValidator;
import com.github.saphyra.selenium.logic.validator.NotificationValidator;
import com.github.saphyra.selenium.test.account.changeusername.BadPasswordTest;
import com.github.saphyra.selenium.test.account.changeusername.EmptyPasswordTest;
import com.github.saphyra.selenium.test.account.changeusername.ExistingUserNameTest;
import com.github.saphyra.selenium.test.account.changeusername.SuccessfulUserNameChangeTest;
import com.github.saphyra.selenium.test.account.changeusername.TooLongUserNameTest;
import com.github.saphyra.selenium.test.account.changeusername.TooShortUserNameTest;
import com.github.saphyra.selenium.test.account.changeusername.helper.ChangeUserNameTestHelper;

import static com.github.saphyra.selenium.logic.util.LinkUtil.ACCOUNT;

public class ChangeUserNameTest extends SeleniumTestApplication {
    private AccountPage accountPage;
    private FieldValidator fieldValidator;
    private ChangeUserNameTestHelper changeUserNameTestHelper;
    private Registration registration;
    private NotificationValidator notificationValidator;
    private Login login;
    private Logout logout;

    @Override
    protected void init() {
        this.accountPage = new AccountPage(driver);
        this.fieldValidator = new FieldValidator(driver, ACCOUNT);
        registration = new Registration(driver, messageCodes);
        this.changeUserNameTestHelper = new ChangeUserNameTestHelper(driver, registration, accountPage, new Navigate(driver));
        this.notificationValidator = new NotificationValidator(driver);
        this.login = new Login(driver, messageCodes);
        this.logout = new Logout(driver, messageCodes);
    }

    @Test
    public void testTooShortUserName() {
        TooShortUserNameTest.builder()
            .accountPage(accountPage)
            .fieldValidator(fieldValidator)
            .changeUserNameTestHelper(changeUserNameTestHelper)
            .messageCodes(messageCodes)
            .build()
            .testTooShortUserName();
    }

    @Test
    public void testTooLongUserName() {
        TooLongUserNameTest.builder()
            .accountPage(accountPage)
            .changeUserNameTestHelper(changeUserNameTestHelper)
            .fieldValidator(fieldValidator)
            .messageCodes(messageCodes)
            .build()
            .testTooLongUserName();
    }

    @Test
    public void testExistingUserName() {
        ExistingUserNameTest.builder()
            .accountPage(accountPage)
            .changeUserNameTestHelper(changeUserNameTestHelper)
            .fieldValidator(fieldValidator)
            .registration(registration)
            .messageCodes(messageCodes)
            .build()
            .testExistingUserName();
    }

    @Test
    public void testEmptyPassword() {
        EmptyPasswordTest.builder()
            .changeUserNameTestHelper(changeUserNameTestHelper)
            .accountPage(accountPage)
            .fieldValidator(fieldValidator)
            .messageCodes(messageCodes)
            .build()
            .testEmptyPassword();
    }

    @Test
    public void testBadPassword() {
        BadPasswordTest.builder()
            .changeUserNameTestHelper(changeUserNameTestHelper)
            .accountPage(accountPage)
            .notificationValidator(notificationValidator)
            .messageCodes(messageCodes)
            .build()
            .testBadPassword();
    }

    @Test
    public void testSuccessfulUserNameChange() {
        SuccessfulUserNameChangeTest.builder()
            .changeUserNameTestHelper(changeUserNameTestHelper)
            .accountPage(accountPage)
            .notificationValidator(notificationValidator)
            .login(login)
            .logout(logout)
            .messageCodes(messageCodes)
            .build()
            .testSuccessfulUserNameChange();
    }
}
