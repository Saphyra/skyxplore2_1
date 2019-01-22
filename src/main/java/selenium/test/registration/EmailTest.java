package selenium.test.registration;

import static selenium.logic.util.LinkUtil.HOST;

import org.junit.Test;

import selenium.SeleniumTestApplication;
import selenium.logic.flow.Logout;
import selenium.logic.flow.Registration;
import selenium.logic.page.IndexPage;
import selenium.logic.validator.FieldValidator;
import selenium.test.registration.email.ExistingEmailTest;
import selenium.test.registration.email.InvalidEmailTest;
import selenium.test.registration.email.helper.EmailTestHelper;

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
        fieldValidator = new FieldValidator(driver, HOST);
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
    public void testExistingEmail(){
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
