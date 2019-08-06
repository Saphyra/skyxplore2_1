package com.github.saphyra.selenium.test.registration;

import com.github.saphyra.selenium.SeleniumTestApplication;
import com.github.saphyra.selenium.logic.flow.Logout;
import com.github.saphyra.selenium.logic.flow.Registration;
import com.github.saphyra.selenium.logic.page.IndexPage;
import com.github.saphyra.selenium.logic.validator.FieldValidator;
import com.github.saphyra.selenium.test.registration.email.ExistingEmailTest;
import com.github.saphyra.selenium.test.registration.email.InvalidEmailTest;
import com.github.saphyra.selenium.test.registration.email.helper.EmailTestHelper;
import org.junit.Test;

import static com.github.saphyra.selenium.logic.util.LinkUtil.INDEX_PAGE;

public class EmailTest extends SeleniumTestApplication {
    private IndexPage indexPage;
    private EmailTestHelper emailTestHelper;
    private FieldValidator fieldValidator;
    private Registration registration;
    private Logout logout;

    @Override
    protected void init() {
        indexPage = new IndexPage(driver);
        emailTestHelper = new EmailTestHelper(driver, indexPage);
        fieldValidator = new FieldValidator(driver, INDEX_PAGE);
        registration = new Registration(driver, messageCodes);
        logout = new Logout(driver, messageCodes);
    }

    @Test
    public void testInvalidEmail() {
        InvalidEmailTest.builder()
            .emailTestHelper(emailTestHelper)
            .indexPage(indexPage)
            .fieldValidator(fieldValidator)
            .messageCodes(messageCodes)
            .build()
            .testInvalidEmail();
    }

    @Test
    public void testExistingEmail() {
        ExistingEmailTest.builder()
            .registration(registration)
            .logout(logout)
            .emailTestHelper(emailTestHelper)
            .indexPage(indexPage)
            .fieldValidator(fieldValidator)
            .messageCodes(messageCodes)
            .build()
            .testExistingEmail();
    }
}
