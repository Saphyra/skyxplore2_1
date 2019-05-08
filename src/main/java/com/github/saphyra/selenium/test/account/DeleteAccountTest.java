package com.github.saphyra.selenium.test.account;

import static com.github.saphyra.selenium.logic.util.LinkUtil.ACCOUNT;

import org.junit.Test;

import com.github.saphyra.selenium.SeleniumTestApplication;
import com.github.saphyra.selenium.logic.flow.Login;
import com.github.saphyra.selenium.logic.flow.Navigate;
import com.github.saphyra.selenium.logic.flow.Registration;
import com.github.saphyra.selenium.logic.page.AccountPage;
import com.github.saphyra.selenium.logic.validator.FieldValidator;
import com.github.saphyra.selenium.logic.validator.NotificationValidator;
import com.github.saphyra.selenium.test.account.deleteaccount.BadPasswordTest;
import com.github.saphyra.selenium.test.account.deleteaccount.EmptyPasswordTest;
import com.github.saphyra.selenium.test.account.deleteaccount.SuccessfulAccountDeletionTest;
import com.github.saphyra.selenium.test.account.deleteaccount.helper.DeleteAccountTestHelper;

public class DeleteAccountTest extends SeleniumTestApplication {
    private DeleteAccountTestHelper deleteAccountTestHelper;
    private AccountPage accountPage;
    private FieldValidator fieldValidator;
    private NotificationValidator notificationValidator;
    private Login login;

    @Override
    protected void init() {
        accountPage = new AccountPage(driver);
        deleteAccountTestHelper = new DeleteAccountTestHelper(driver, new Registration(driver, messageCodes), new Navigate(driver), accountPage);
        fieldValidator = new FieldValidator(driver, ACCOUNT);
        notificationValidator = new NotificationValidator(driver);
        login = new Login(driver, messageCodes);
    }

    @Test
    public void testEmptyPassword() {
        EmptyPasswordTest.builder()
            .deleteAccountTestHelper(deleteAccountTestHelper)
            .accountPage(accountPage)
            .fieldValidator(fieldValidator)
            .messageCodes(messageCodes)
            .build()
            .testEmptyPassword();
    }

    @Test
    public void testBadPassword() {
        BadPasswordTest.builder()
            .deleteAccountTestHelper(deleteAccountTestHelper)
            .driver(driver)
            .accountPage(accountPage)
            .notificationValidator(notificationValidator)
            .messageCodes(messageCodes)
            .build()
            .testBadPassword();
    }

    @Test
    public void testSuccessfulAccountDeletion() {
        SuccessfulAccountDeletionTest.builder()
            .deleteAccountTestHelper(deleteAccountTestHelper)
            .driver(driver)
            .accountPage(accountPage)
            .notificationValidator(notificationValidator)
            .login(login)
            .messageCodes(messageCodes)
            .build()
            .testSuccessfulAccountDeletion();
    }
}
