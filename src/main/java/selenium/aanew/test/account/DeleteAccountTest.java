package selenium.aanew.test.account;

import static selenium.aanew.logic.util.LinkUtil.ACCOUNT;

import org.junit.Test;

import selenium.aanew.SeleniumTestApplication;
import selenium.aanew.logic.flow.Login;
import selenium.aanew.logic.flow.Navigate;
import selenium.aanew.logic.flow.Registration;
import selenium.aanew.logic.page.AccountPage;
import selenium.aanew.logic.validator.FieldValidator;
import selenium.aanew.logic.validator.NotificationValidator;
import selenium.aanew.test.account.deleteaccount.BadPasswordTest;
import selenium.aanew.test.account.deleteaccount.EmptyPasswordTest;
import selenium.aanew.test.account.deleteaccount.SuccessfulAccountDeletionTest;
import selenium.aanew.test.account.deleteaccount.helper.DeleteAccountTestHelper;

public class DeleteAccountTest extends SeleniumTestApplication {
    private DeleteAccountTestHelper deleteAccountTestHelper;
    private AccountPage accountPage;
    private FieldValidator fieldValidator;
    private NotificationValidator notificationValidator;
    private Login login;

    @Override
    protected void init() {
        /*
        testCase.validateBadPassword();
        testCase.validateSuccess();
         */
        accountPage = new AccountPage(driver);
        deleteAccountTestHelper = new DeleteAccountTestHelper(driver, new Registration(driver), new Navigate(driver), accountPage);
        fieldValidator = new FieldValidator(driver, ACCOUNT);
        notificationValidator = new NotificationValidator(driver);
        login = new Login(driver);
    }

    @Test
    public void testEmptyPassword(){
        EmptyPasswordTest.builder()
            .deleteAccountTestHelper(deleteAccountTestHelper)
            .accountPage(accountPage)
            .fieldValidator(fieldValidator)
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
            .build()
            .testSuccessfulAccountDeletion();
    }
}
