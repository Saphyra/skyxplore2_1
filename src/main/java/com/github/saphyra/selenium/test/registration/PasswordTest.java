package com.github.saphyra.selenium.test.registration;

import com.github.saphyra.selenium.SeleniumTestApplication;
import com.github.saphyra.selenium.logic.page.IndexPage;
import com.github.saphyra.selenium.logic.validator.FieldValidator;
import com.github.saphyra.selenium.test.registration.password.ConfirmPasswordTest;
import com.github.saphyra.selenium.test.registration.password.TooLongPasswordTest;
import com.github.saphyra.selenium.test.registration.password.TooShortPasswordTest;
import com.github.saphyra.selenium.test.registration.password.helper.PasswordTestHelper;
import org.junit.Test;

import static com.github.saphyra.selenium.logic.util.LinkUtil.INDEX_PAGE;

public class PasswordTest extends SeleniumTestApplication {
    private PasswordTestHelper passwordTestHelper;
    private IndexPage indexPage;
    private FieldValidator fieldValidator;

    @Override
    protected void init() {
        indexPage = new IndexPage(driver);
        passwordTestHelper = new PasswordTestHelper(driver, indexPage);
        fieldValidator = new FieldValidator(driver, INDEX_PAGE);
    }

    @Test
    public void testTooShortPassword() {
        TooShortPasswordTest.builder()
            .passwordTestHelper(passwordTestHelper)
            .indexPage(indexPage)
            .fieldValidator(fieldValidator)
            .build()
            .testTooShortPassword();
    }

    @Test
    public void testTooLongPassword() {
        TooLongPasswordTest.builder()
            .passwordTestHelper(passwordTestHelper)
            .fieldValidator(fieldValidator)
            .indexPage(indexPage)
            .build()
            .testTooLongPassword();
    }

    @Test
    public void testConfirmPassword() {
        ConfirmPasswordTest.builder()
            .passwordTestHelper(passwordTestHelper)
            .indexPage(indexPage)
            .fieldValidator(fieldValidator)
            .build()
            .testConfirmPassword();
    }
}
