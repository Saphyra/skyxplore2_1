package selenium.test.account;

import org.junit.Test;
import selenium.SeleniumTestApplication;
import selenium.logic.flow.Login;
import selenium.logic.flow.Logout;
import selenium.logic.flow.Navigate;
import selenium.logic.flow.Registration;
import selenium.logic.page.AccountPage;
import selenium.logic.validator.FieldValidator;
import selenium.logic.validator.NotificationValidator;
import selenium.test.account.changepassword.BadConfirmPasswordTest;
import selenium.test.account.changepassword.BadPasswordTest;
import selenium.test.account.changepassword.EmptyCurrentPasswordTest;
import selenium.test.account.changepassword.SuccessfulPasswordChangeTest;
import selenium.test.account.changepassword.TooLongPasswordTest;
import selenium.test.account.changepassword.TooShortPasswordTest;
import selenium.test.account.changepassword.helper.ChangePasswordTestHelper;

import static selenium.logic.util.LinkUtil.ACCOUNT;

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
            .build()
            .testTooLongPassword();
    }

    @Test
    public void testBadConfirmPassword() {
        BadConfirmPasswordTest.builder()
            .changePasswordTestHelper(changePasswordTestHelper)
            .accountPage(accountPage)
            .fieldValidator(fieldValidator)
            .build()
            .testBadConfirmPassword();
    }

    @Test
    public void testEmptyCurrentPassword() {
        EmptyCurrentPasswordTest.builder()
            .changePasswordTestHelper(changePasswordTestHelper)
            .accountPage(accountPage)
            .fieldValidator(fieldValidator)
            .build()
            .testEmptyCurrentPassword();
    }

    @Test
    public void testBadPassword() {
        BadPasswordTest.builder()
            .changePasswordTestHelper(changePasswordTestHelper)
            .accountPage(accountPage)
            .notificationValidator(notificationValidator)
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
            .build()
            .testSuccessfulPasswordChange();
    }
}
