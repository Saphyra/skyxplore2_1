package com.github.saphyra.selenium.test.account;

import org.junit.Ignore;
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
        registration = new Registration(driver);
        this.changeUserNameTestHelper = new ChangeUserNameTestHelper(driver, registration, accountPage, new Navigate(driver));
        this.notificationValidator = new NotificationValidator(driver);
        this.login = new Login(driver);
        this.logout = new Logout(driver);
    }

    @Test
    public void testTooShortUserName() {
        TooShortUserNameTest.builder()
            .accountPage(accountPage)
            .fieldValidator(fieldValidator)
            .changeUserNameTestHelper(changeUserNameTestHelper)
            .driver(driver)
            .build()
            .testTooShortUserName();
    }

    @Test
    public void testTooLongUserName() {
        TooLongUserNameTest.builder()
            .accountPage(accountPage)
            .changeUserNameTestHelper(changeUserNameTestHelper)
            .fieldValidator(fieldValidator)
            .driver(driver)
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
            .driver(driver)
            .build()
            .testExistingUserName();
    }

    @Test
    public void testEmptyPassword() {
        EmptyPasswordTest.builder()
            .changeUserNameTestHelper(changeUserNameTestHelper)
            .accountPage(accountPage)
            .fieldValidator(fieldValidator)
            .driver(driver)
            .build()
            .testEmptyPassword();
    }

    @Test
    @Ignore
    //TODO restore after exception handling finished
    public void testBadPassword() {
        BadPasswordTest.builder()
            .changeUserNameTestHelper(changeUserNameTestHelper)
            .accountPage(accountPage)
            .notificationValidator(notificationValidator)
            .driver(driver)
            .build()
            .testBadPassword();
    }

    @Test
    @Ignore
    //TODO restore after exception handling finished
    public void testSuccessfulUserNameChange() {
        SuccessfulUserNameChangeTest.builder()
            .changeUserNameTestHelper(changeUserNameTestHelper)
            .accountPage(accountPage)
            .notificationValidator(notificationValidator)
            .login(login)
            .logout(logout)
            .driver(driver)
            .build()
            .testSuccessfulUserNameChange();
    }
}
