package com.github.saphyra.selenium.test.account;

import org.junit.Test;
import com.github.saphyra.selenium.SeleniumTestApplication;
import com.github.saphyra.selenium.logic.flow.Navigate;
import com.github.saphyra.selenium.logic.flow.Registration;
import com.github.saphyra.selenium.logic.page.AccountPage;
import com.github.saphyra.selenium.logic.validator.FieldValidator;
import com.github.saphyra.selenium.logic.validator.NotificationValidator;
import com.github.saphyra.selenium.test.account.changeemail.WrongPasswordTest;
import com.github.saphyra.selenium.test.account.changeemail.EmptyPasswordTest;
import com.github.saphyra.selenium.test.account.changeemail.ExistingEmailTest;
import com.github.saphyra.selenium.test.account.changeemail.InvalidEmailTest;
import com.github.saphyra.selenium.test.account.changeemail.SuccessfulEmailChangeTest;
import com.github.saphyra.selenium.test.account.changeemail.helper.ChangeEmailTestHelper;

import static com.github.saphyra.selenium.logic.util.LinkUtil.ACCOUNT;

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
        WrongPasswordTest.builder()
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
