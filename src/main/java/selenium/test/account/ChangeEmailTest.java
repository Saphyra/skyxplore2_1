package selenium.test.account;

import org.junit.Test;
import selenium.SeleniumTestApplication;
import selenium.logic.flow.Navigate;
import selenium.logic.flow.Registration;
import selenium.logic.page.AccountPage;
import selenium.logic.validator.FieldValidator;
import selenium.logic.validator.NotificationValidator;
import selenium.test.account.changeemail.BadPasswordTest;
import selenium.test.account.changeemail.EmptyPasswordTest;
import selenium.test.account.changeemail.ExistingEmailTest;
import selenium.test.account.changeemail.InvalidEmailTest;
import selenium.test.account.changeemail.SuccessfulEmailChangeTest;
import selenium.test.account.changeemail.helper.ChangeEmailTestHelper;

import static selenium.logic.util.LinkUtil.ACCOUNT;

public class ChangeEmailTest extends SeleniumTestApplication {
    private ChangeEmailTestHelper changeEmailTestHelper;
    private FieldValidator fieldValidator;
    private AccountPage accountPage;
    private Registration registration;
    private NotificationValidator notificationValidator;

    @Override
    protected void init() {
        fieldValidator = new FieldValidator(driver, ACCOUNT);
        accountPage = new AccountPage(driver);
        registration = new Registration(driver, messageCodes);
        Navigate navigate = new Navigate(driver);
        changeEmailTestHelper = new ChangeEmailTestHelper(accountPage, registration, navigate, driver);
        notificationValidator = new NotificationValidator(driver);
    }

    @Test
    public void testInvalidEmail() {
        InvalidEmailTest.builder()
            .changeEmailTestHelper(changeEmailTestHelper)
            .fieldValidator(fieldValidator)
            .accountPage(accountPage)
            .build()
            .testInvalidEmail();
    }

    @Test
    public void testExistingEmail() {
        ExistingEmailTest.builder()
            .registration(registration)
            .changeEmailTestHelper(changeEmailTestHelper)
            .accountPage(accountPage)
            .fieldValidator(fieldValidator)
            .build()
            .testExistingEmail();
    }

    @Test
    public void testEmptyPassword() {
        EmptyPasswordTest.builder()
            .changeEmailTestHelper(changeEmailTestHelper)
            .accountPage(accountPage)
            .fieldValidator(fieldValidator)
            .build()
            .testEmptyPassword();
    }

    @Test
    public void badPasswordTest(){
        BadPasswordTest.builder()
            .accountPage(accountPage)
            .changeEmailTestHelper(changeEmailTestHelper)
            .fieldValidator(fieldValidator)
            .notificationValidator(notificationValidator)
            .build()
            .testBadPassword();
    }

    @Test
    public void successfulEmailChangeTest(){
        SuccessfulEmailChangeTest.builder()
            .accountPage(accountPage)
            .changeEmailTestHelper(changeEmailTestHelper)
            .notificationValidator(notificationValidator)
            .build()
            .testSuccessfulEmailChange();
    }
}
