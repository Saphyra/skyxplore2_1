package selenium.aanew.test.account;

import static selenium.aanew.logic.util.LinkUtil.ACCOUNT;

import org.junit.Test;

import selenium.aanew.SeleniumTestApplication;
import selenium.aanew.logic.flow.Navigate;
import selenium.aanew.logic.flow.Registration;
import selenium.aanew.logic.page.AccountPage;
import selenium.aanew.logic.validator.FieldValidator;
import selenium.aanew.test.account.deleteaccount.EmptyPasswordTest;
import selenium.aanew.test.account.deleteaccount.helper.DeleteAccountTestHelper;

public class DeleteAccountTest extends SeleniumTestApplication {
    private DeleteAccountTestHelper deleteAccountTestHelper;
    private AccountPage accountPage;
    private FieldValidator fieldValidator;

    @Override
    protected void init() {
        /*
        testCase.validateBadPassword();
        testCase.validateSuccess();
         */
        accountPage = new AccountPage(driver);
        deleteAccountTestHelper = new DeleteAccountTestHelper(new Registration(driver), new Navigate(driver), accountPage);
        fieldValidator = new FieldValidator(driver, ACCOUNT);
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

    }
}
