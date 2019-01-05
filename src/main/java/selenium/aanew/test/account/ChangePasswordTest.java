package selenium.aanew.test.account;

import org.junit.Test;
import selenium.aanew.SeleniumTestApplication;
import selenium.aanew.logic.flow.Login;
import selenium.aanew.logic.flow.Logout;
import selenium.aanew.logic.flow.Navigate;
import selenium.aanew.logic.flow.Registration;
import selenium.aanew.logic.page.AccountPage;
import selenium.aanew.logic.validator.FieldValidator;
import selenium.aanew.logic.validator.NotificationValidator;
import selenium.aanew.test.account.changepassword.BadConfirmPasswordTest;
import selenium.aanew.test.account.changepassword.BadPasswordTest;
import selenium.aanew.test.account.changepassword.EmptyCurrentPasswordTest;
import selenium.aanew.test.account.changepassword.SuccessfulPasswordChangeTest;
import selenium.aanew.test.account.changepassword.TooLongPasswordTest;
import selenium.aanew.test.account.changepassword.TooShortPasswordTest;
import selenium.aanew.test.account.changepassword.helper.ChangePasswordTestHelper;

import static selenium.aanew.logic.util.LinkUtil.ACCOUNT;

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
