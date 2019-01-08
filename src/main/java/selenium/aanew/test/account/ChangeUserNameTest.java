package selenium.aanew.test.account;

import static selenium.aanew.logic.util.LinkUtil.ACCOUNT;

import org.junit.Test;

import selenium.aanew.SeleniumTestApplication;
import selenium.aanew.logic.flow.Login;
import selenium.aanew.logic.flow.Logout;
import selenium.aanew.logic.flow.Navigate;
import selenium.aanew.logic.flow.Registration;
import selenium.aanew.logic.page.AccountPage;
import selenium.aanew.logic.validator.FieldValidator;
import selenium.aanew.logic.validator.NotificationValidator;
import selenium.aanew.test.account.changeusername.BadPasswordTest;
import selenium.aanew.test.account.changeusername.EmptyPasswordTest;
import selenium.aanew.test.account.changeusername.ExistingUserNameTest;
import selenium.aanew.test.account.changeusername.SuccessfulUserNameChangeTest;
import selenium.aanew.test.account.changeusername.TooLongUserNameTest;
import selenium.aanew.test.account.changeusername.TooShortUserNameTest;
import selenium.aanew.test.account.changeusername.helper.ChangeUserNameTestHelper;

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
