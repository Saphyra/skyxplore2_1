package org.github.saphyra.selenium.test.account;

import org.junit.Test;
import org.github.saphyra.selenium.SeleniumTestApplication;
import org.github.saphyra.selenium.logic.flow.Navigate;
import org.github.saphyra.selenium.logic.flow.Registration;
import org.github.saphyra.selenium.logic.page.AccountPage;
import org.github.saphyra.selenium.logic.validator.FieldValidator;
import org.github.saphyra.selenium.logic.validator.NotificationValidator;
import org.github.saphyra.selenium.test.account.changeemail.BadPasswordTest;
import org.github.saphyra.selenium.test.account.changeemail.EmptyPasswordTest;
import org.github.saphyra.selenium.test.account.changeemail.ExistingEmailTest;
import org.github.saphyra.selenium.test.account.changeemail.InvalidEmailTest;
import org.github.saphyra.selenium.test.account.changeemail.SuccessfulEmailChangeTest;
import org.github.saphyra.selenium.test.account.changeemail.helper.ChangeEmailTestHelper;

import static org.github.saphyra.selenium.logic.util.LinkUtil.ACCOUNT;

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
            .messageCodes(messageCodes)
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
            .messageCodes(messageCodes)
            .build()
            .testExistingEmail();
    }

    @Test
    public void testEmptyPassword() {
        EmptyPasswordTest.builder()
            .changeEmailTestHelper(changeEmailTestHelper)
            .accountPage(accountPage)
            .fieldValidator(fieldValidator)
            .messageCodes(messageCodes)
            .build()
            .testEmptyPassword();
    }

    @Test
    public void badPasswordTest() {
        BadPasswordTest.builder()
            .accountPage(accountPage)
            .changeEmailTestHelper(changeEmailTestHelper)
            .fieldValidator(fieldValidator)
            .notificationValidator(notificationValidator)
            .messageCodes(messageCodes)
            .build()
            .testBadPassword();
    }

    @Test
    public void successfulEmailChangeTest() {
        SuccessfulEmailChangeTest.builder()
            .accountPage(accountPage)
            .changeEmailTestHelper(changeEmailTestHelper)
            .notificationValidator(notificationValidator)
            .messageCodes(messageCodes)
            .build()
            .testSuccessfulEmailChange();
    }
}
