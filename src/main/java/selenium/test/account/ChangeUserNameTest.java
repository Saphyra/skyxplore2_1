package selenium.test.account;

import static selenium.logic.util.LinkUtil.ACCOUNT;

import org.junit.Test;

import selenium.SeleniumTestApplication;
import selenium.logic.flow.Login;
import selenium.logic.flow.Logout;
import selenium.logic.flow.Navigate;
import selenium.logic.flow.Registration;
import selenium.logic.page.AccountPage;
import selenium.logic.validator.FieldValidator;
import selenium.logic.validator.NotificationValidator;
import selenium.test.account.changeusername.BadPasswordTest;
import selenium.test.account.changeusername.EmptyPasswordTest;
import selenium.test.account.changeusername.ExistingUserNameTest;
import selenium.test.account.changeusername.SuccessfulUserNameChangeTest;
import selenium.test.account.changeusername.TooLongUserNameTest;
import selenium.test.account.changeusername.TooShortUserNameTest;
import selenium.test.account.changeusername.helper.ChangeUserNameTestHelper;

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
        /*
        testCase.validateHappyPath();
         */

        this.accountPage = new AccountPage(driver);
        this.fieldValidator = new FieldValidator(driver, ACCOUNT);
        registration = new Registration(driver, messageCodes);
        this.changeUserNameTestHelper = new ChangeUserNameTestHelper(driver, registration, accountPage, new Navigate(driver));
        this.notificationValidator = new NotificationValidator(driver);
        this.login = new Login(driver);
        this.logout = new Logout(driver, messageCodes);
    }

    @Test
    public void testTooShortUserName() {
        TooShortUserNameTest.builder()
            .accountPage(accountPage)
            .fieldValidator(fieldValidator)
            .changeUserNameTestHelper(changeUserNameTestHelper)
            .build()
            .testTooShortUserName();
    }

    @Test
    public void testTooLongUserName() {
        TooLongUserNameTest.builder()
            .accountPage(accountPage)
            .changeUserNameTestHelper(changeUserNameTestHelper)
            .fieldValidator(fieldValidator)
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
            .build()
            .testExistingUserName();
    }

    @Test
    public void testEmptyPassword() {
        EmptyPasswordTest.builder()
            .changeUserNameTestHelper(changeUserNameTestHelper)
            .accountPage(accountPage)
            .fieldValidator(fieldValidator)
            .build()
            .testEmptyPassword();
    }

    @Test
    public void testBadPassword() {
        BadPasswordTest.builder()
            .changeUserNameTestHelper(changeUserNameTestHelper)
            .accountPage(accountPage)
            .notificationValidator(notificationValidator)
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
            .build()
            .testSuccessfulUserNameChange();
    }
}
