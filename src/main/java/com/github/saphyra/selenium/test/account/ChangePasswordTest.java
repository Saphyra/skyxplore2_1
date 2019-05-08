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
import com.github.saphyra.selenium.test.account.changepassword.BadConfirmPasswordTest;
import com.github.saphyra.selenium.test.account.changepassword.BadPasswordTest;
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
        this.registration = new Registration(driver, messageCodes);
        this.accountPage = new AccountPage(driver);
        this.fieldValidator = new FieldValidator(driver, ACCOUNT);
        this.navigate = new Navigate(driver);
        this.changePasswordTestHelper = new ChangePasswordTestHelper(driver, accountPage, registration, navigate);
        this.notificationValidator = new NotificationValidator(driver);
        this.logout = new Logout(driver, messageCodes);
        this.login = new Login(driver, messageCodes);
    }

    @Test
    public void testTooShortPassword() {
        TooShortPasswordTest.builder()
            .driver(driver)
            .accountPage(accountPage)
            .fieldValidator(fieldValidator)
            .navigate(navigate)
            .changePasswordTestHelper(changePasswordTestHelper)
            .messageCodes(messageCodes)
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
            .messageCodes(messageCodes)
            .build()
            .testTooLongPassword();
    }

    @Test
    public void testBadConfirmPassword() {
        BadConfirmPasswordTest.builder()
            .changePasswordTestHelper(changePasswordTestHelper)
            .accountPage(accountPage)
            .fieldValidator(fieldValidator)
            .messageCodes(messageCodes)
            .build()
            .testBadConfirmPassword();
    }

    @Test
    public void testEmptyCurrentPassword() {
        EmptyCurrentPasswordTest.builder()
            .changePasswordTestHelper(changePasswordTestHelper)
            .accountPage(accountPage)
            .fieldValidator(fieldValidator)
            .messageCodes(messageCodes)
            .build()
            .testEmptyCurrentPassword();
    }

    @Test
    public void testBadPassword() {
        BadPasswordTest.builder()
            .changePasswordTestHelper(changePasswordTestHelper)
            .accountPage(accountPage)
            .notificationValidator(notificationValidator)
            .messageCodes(messageCodes)
            .build()
            .testBadPassword();
    }

    @Test
    public void testSuccessfulPasswordChange() {
        SuccessfulPasswordChangeTest.builder()
            .changePasswordTestHelper(changePasswordTestHelper)
            .accountPage(accountPage)
            .logout(logout)
            .login(login)
            .navigate(navigate)
            .notificationValidator(notificationValidator)
            .messageCodes(messageCodes)
            .build()
            .testSuccessfulPasswordChange();
    }
}
