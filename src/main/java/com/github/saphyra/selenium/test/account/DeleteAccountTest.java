package com.github.saphyra.selenium.test.account;

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
import org.junit.Ignore;
import org.junit.Test;

import static com.github.saphyra.selenium.logic.util.LinkUtil.ACCOUNT;

public class DeleteAccountTest extends SeleniumTestApplication {
    private DeleteAccountTestHelper deleteAccountTestHelper;
    private AccountPage accountPage;
    private FieldValidator fieldValidator;
    private NotificationValidator notificationValidator;
    private Login login;

    @Override
    protected void init() {
        accountPage = new AccountPage(driver);
        deleteAccountTestHelper = new DeleteAccountTestHelper(driver, new Registration(driver), new Navigate(driver), accountPage);
        fieldValidator = new FieldValidator(driver, ACCOUNT);
        notificationValidator = new NotificationValidator(driver);
        login = new Login(driver);
    }

    @Test
    public void testEmptyPassword() {
        EmptyPasswordTest.builder()
            .driver(driver)
            .deleteAccountTestHelper(deleteAccountTestHelper)
            .accountPage(accountPage)
            .fieldValidator(fieldValidator)
            .build()
            .testEmptyPassword();
    }

    @Test
    @Ignore
    //TODO restore
    public void testBadPassword() {
        BadPasswordTest.builder()
            .deleteAccountTestHelper(deleteAccountTestHelper)
            .driver(driver)
            .accountPage(accountPage)
            .notificationValidator(notificationValidator)
            .build()
            .testBadPassword();
    }

    @Test
    @Ignore
    //TODO restore
    public void testSuccessfulAccountDeletion() {
        SuccessfulAccountDeletionTest.builder()
            .deleteAccountTestHelper(deleteAccountTestHelper)
            .driver(driver)
            .accountPage(accountPage)
            .notificationValidator(notificationValidator)
            .login(login)
            .build()
            .testSuccessfulAccountDeletion();
    }
}
