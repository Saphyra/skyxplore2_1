package selenium.aanew.test.account;

import org.junit.Test;
import selenium.aanew.SeleniumTestApplication;
import selenium.aanew.logic.flow.Navigate;
import selenium.aanew.logic.flow.Registration;
import selenium.aanew.logic.page.AccountPage;
import selenium.aanew.logic.validator.FieldValidator;
import selenium.aanew.logic.validator.NotificationValidator;
import selenium.aanew.test.account.changeemail.BadPasswordTest;
import selenium.aanew.test.account.changeemail.EmptyPasswordTest;
import selenium.aanew.test.account.changeemail.ExistingEmailTest;
import selenium.aanew.test.account.changeemail.InvalidEmailTest;
import selenium.aanew.test.account.changeemail.helper.ChangeEmailTestHelper;

import static selenium.aanew.logic.util.LinkUtil.ACCOUNT;

public class ChangeEmailTest extends SeleniumTestApplication {
    private ChangeEmailTestHelper changeEmailTestHelper;
    private FieldValidator fieldValidator;
    private AccountPage accountPage;
    private Registration registration;
    private Navigate navigate;
    private NotificationValidator notificationValidator;

    @Override
    protected void init() {
        fieldValidator = new FieldValidator(driver, ACCOUNT);
        accountPage = new AccountPage(driver);
        registration = new Registration(driver);
        navigate = new Navigate(driver);
        changeEmailTestHelper = new ChangeEmailTestHelper(accountPage, registration, navigate);
        notificationValidator = new NotificationValidator(driver);
        /*
        testCase.validateHappyPath();
         */
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
            .driver(driver)
            .fieldValidator(fieldValidator)
            .notificationValidator(notificationValidator)
            .build()
            .testBadPassword();
    }
}
