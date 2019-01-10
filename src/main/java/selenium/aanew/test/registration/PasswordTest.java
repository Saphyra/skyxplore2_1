package selenium.aanew.test.registration;

import org.junit.Test;
import selenium.aanew.SeleniumTestApplication;
import selenium.aanew.logic.page.IndexPage;
import selenium.aanew.logic.validator.FieldValidator;
import selenium.aanew.test.registration.password.TooLongPasswordTest;
import selenium.aanew.test.registration.password.TooShortPasswordTest;
import selenium.aanew.test.registration.password.helper.PasswordTestHelper;

import static selenium.aanew.logic.util.LinkUtil.HOST;

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
    public void testTooShortPassword(){
        TooShortPasswordTest.builder()
            .passwordTestHelper(passwordTestHelper)
            .indexPage(indexPage)
            .fieldValidator(fieldValidator)
            .build()
            .testTooShortPassword();
    }

    @Test
    public void testTooLongPassword(){
        TooLongPasswordTest.builder()
            .passwordTestHelper(passwordTestHelper)
            .fieldValidator(fieldValidator)
            .indexPage(indexPage)
            .build()
            .testTooLongPassword();
    }
}
