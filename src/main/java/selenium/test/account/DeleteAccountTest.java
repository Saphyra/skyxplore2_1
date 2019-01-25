package selenium.test.account;

import static selenium.logic.util.LinkUtil.ACCOUNT;

import org.junit.Test;

import selenium.SeleniumTestApplication;
import selenium.logic.flow.Login;
import selenium.logic.flow.Navigate;
import selenium.logic.flow.Registration;
import selenium.logic.page.AccountPage;
import selenium.logic.validator.FieldValidator;
import selenium.logic.validator.NotificationValidator;
import selenium.test.account.deleteaccount.BadPasswordTest;
import selenium.test.account.deleteaccount.EmptyPasswordTest;
import selenium.test.account.deleteaccount.SuccessfulAccountDeletionTest;
import selenium.test.account.deleteaccount.helper.DeleteAccountTestHelper;

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
    public void testEmptyPassword(){
        EmptyPasswordTest.builder()
            .deleteAccountTestHelper(deleteAccountTestHelper)
            .accountPage(accountPage)
            .fieldValidator(fieldValidator)
            .messageCodes(messageCodes)
            .build()
            .testEmptyPassword();
    }

    @Test
    public void testBadPassword(){
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
    public void testSuccessfulAccountDeletion(){
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
