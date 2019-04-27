package selenium.test.registration;

import static selenium.logic.util.LinkUtil.HOST;

import org.junit.Test;

import selenium.SeleniumTestApplication;
import selenium.logic.page.IndexPage;
import selenium.logic.validator.FieldValidator;
import selenium.test.registration.password.ConfirmPasswordTest;
import selenium.test.registration.password.TooLongPasswordTest;
import selenium.test.registration.password.TooShortPasswordTest;
import selenium.test.registration.password.helper.PasswordTestHelper;

public class PasswordTest extends SeleniumTestApplication {
    private PasswordTestHelper passwordTestHelper;
    private IndexPage indexPage;
    private FieldValidator fieldValidator;

    @Override
    protected void init() {
        indexPage = new IndexPage(driver);
        passwordTestHelper = new PasswordTestHelper(driver, indexPage);
        fieldValidator = new FieldValidator(driver, HOST);
    }

    @Test
    public void testTooShortPassword() {
        TooShortPasswordTest.builder()
            .passwordTestHelper(passwordTestHelper)
            .indexPage(indexPage)
            .fieldValidator(fieldValidator)
            .messageCodes(messageCodes)
            .build()
            .testTooShortPassword();
    }

    @Test
    public void testTooLongPassword() {
        TooLongPasswordTest.builder()
            .passwordTestHelper(passwordTestHelper)
            .fieldValidator(fieldValidator)
            .indexPage(indexPage)
            .messageCodes(messageCodes)
            .build()
            .testTooLongPassword();
    }

    @Test
    public void testConfirmPassword() {
        ConfirmPasswordTest.builder()
            .passwordTestHelper(passwordTestHelper)
            .indexPage(indexPage)
            .fieldValidator(fieldValidator)
            .messageCodes(messageCodes)
            .build()
            .testConfirmPassword();
    }
}
