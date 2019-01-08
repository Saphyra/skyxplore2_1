package selenium.aanew.test.account;

import static selenium.aanew.logic.util.LinkUtil.ACCOUNT;

import org.junit.Test;

import selenium.aanew.SeleniumTestApplication;
import selenium.aanew.logic.flow.Navigate;
import selenium.aanew.logic.flow.Registration;
import selenium.aanew.logic.page.AccountPage;
import selenium.aanew.logic.validator.FieldValidator;
import selenium.aanew.test.account.changeusername.TooLongUserNameTest;
import selenium.aanew.test.account.changeusername.TooShortUserNameTest;
import selenium.aanew.test.account.changeusername.helper.ChangeUserNameTestHelper;

public class ChangeUserNameTest extends SeleniumTestApplication {
    private AccountPage accountPage;
    private FieldValidator fieldValidator;
    private ChangeUserNameTestHelper changeUserNameTestHelper;

    @Override
    protected void init() {
        /*
        testCase.validateTooLongUserName();
        testCase.validateExistingUserName();
        testCase.validateEmptyPassword();
        testCase.validateBadPassword();
        testCase.validateHappyPath();
         */

        this.accountPage = new AccountPage(driver);
        this.fieldValidator = new FieldValidator(driver, ACCOUNT);
        this.changeUserNameTestHelper = new ChangeUserNameTestHelper(new Registration(driver), accountPage, new Navigate(driver));
    }

    @Test
    public void testTooShortUserName(){
        TooShortUserNameTest.builder()
            .accountPage(accountPage)
            .fieldValidator(fieldValidator)
            .changeUserNameTestHelper(changeUserNameTestHelper)
            .build()
            .testTooShortUserName();
    }

    @Test
    public void testTooLongUserName(){
        TooLongUserNameTest.builder()
            .accountPage(accountPage)
            .changeUserNameTestHelper(changeUserNameTestHelper)
            .fieldValidator(fieldValidator)
            .build()
            .testTooLongUserName();
    }
}
