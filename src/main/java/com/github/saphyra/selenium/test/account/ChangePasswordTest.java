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
import com.github.saphyra.selenium.test.account.changepassword.BadConfirmPasswordTest;
import com.github.saphyra.selenium.test.account.changepassword.WrongPasswordTest;
import com.github.saphyra.selenium.test.account.changepassword.EmptyCurrentPasswordTest;
import com.github.saphyra.selenium.test.account.changepassword.SuccessfulPasswordChangeTest;
import com.github.saphyra.selenium.test.account.changepassword.TooLongPasswordTest;
import com.github.saphyra.selenium.test.account.changepassword.TooShortPasswordTest;
import com.github.saphyra.selenium.test.account.changepassword.helper.ChangePasswordTestHelper;

import static com.github.saphyra.selenium.logic.util.LinkUtil.ACCOUNT;

public class ChangePasswordTest extends SeleniumTestApplication {
    private Registration registration;
    private AccountPage accountPage;
    private FieldValidator fieldValidator;
    private Navigate navigate;
    private ChangePasswordTestHelper changePasswordTestHelper;
    private NotificationValidator notificationValidator;
    private Logout logout;
    private Login login;

    @Override
    protected void init() {
        this.registration = new Registration(driver);
        this.accountPage = new AccountPage(driver);
        this.fieldValidator = new FieldValidator(driver, ACCOUNT);
        this.navigate = new Navigate(driver);
        this.changePasswordTestHelper = new ChangePasswordTestHelper(driver, accountPage, registration, navigate);
        this.notificationValidator = new NotificationValidator(driver);
        this.logout = new Logout(driver);
        this.login = new Login(driver);
    }

    @Test
    public void testTooShortPassword() {
        TooShortPasswordTest.builder()
            .driver(driver)
            .accountPage(accountPage)
            .fieldValidator(fieldValidator)
            .navigate(navigate)
            .changePasswordTestHelper(changePasswordTestHelper)
            .build()
            .testTooShortPassword();
    }

    @Test
    public void testTooLongPassword() {
        TooLongPasswordTest.builder()
            .changePasswordTestHelper(changePasswordTestHelper)
            .registration(registration)
            .navigate(navigate)
            .accountPage(accountPage)
            .fieldValidator(fieldValidator)
            .driver(driver)
            .build()
            .testTooLongPassword();
    }

    @Test
    public void testBadConfirmPassword() {
        BadConfirmPasswordTest.builder()
            .changePasswordTestHelper(changePasswordTestHelper)
            .accountPage(accountPage)
            .fieldValidator(fieldValidator)
            .driver(driver)
            .build()
            .testBadConfirmPassword();
    }

    @Test
    public void testEmptyCurrentPassword() {
        EmptyCurrentPasswordTest.builder()
            .changePasswordTestHelper(changePasswordTestHelper)
            .accountPage(accountPage)
            .fieldValidator(fieldValidator)
            .driver(driver)
            .build()
            .testEmptyCurrentPassword();
    }

    @Test
    @Ignore
    //TODO restore after exception handling finished
    public void testBadPassword() {
        WrongPasswordTest.builder()
            .changePasswordTestHelper(changePasswordTestHelper)
            .accountPage(accountPage)
            .notificationValidator(notificationValidator)
            .driver(driver)
            .build()
            .testBadPassword();
    }

    @Test
    //TODO restore after exception handling finished
    @Ignore
    public void testSuccessfulPasswordChange() {
        SuccessfulPasswordChangeTest.builder()
            .driver(driver)
            .changePasswordTestHelper(changePasswordTestHelper)
            .accountPage(accountPage)
            .logout(logout)
            .login(login)
            .navigate(navigate)
            .notificationValidator(notificationValidator)
            .build()
            .testSuccessfulPasswordChange();
    }
}
