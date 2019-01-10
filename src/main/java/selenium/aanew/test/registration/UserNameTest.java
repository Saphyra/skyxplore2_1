package selenium.aanew.test.registration;

import org.junit.Test;
import selenium.aanew.SeleniumTestApplication;
import selenium.aanew.logic.flow.Logout;
import selenium.aanew.logic.flow.Registration;
import selenium.aanew.logic.page.IndexPage;
import selenium.aanew.logic.validator.FieldValidator;
import selenium.aanew.test.registration.username.ExistingUserNameTest;
import selenium.aanew.test.registration.username.TooLongUserNameTest;
import selenium.aanew.test.registration.username.TooShortUserNameTest;
import selenium.aanew.test.registration.username.helper.UserNameTestHelper;

import static selenium.aanew.logic.util.LinkUtil.HOST;

public class UserNameTest extends SeleniumTestApplication {
    private UserNameTestHelper userNameTestHelper;
    private IndexPage indexPage;
    private FieldValidator fieldValidator;
    private Registration registration;
    private Logout logout;

    @Override
    protected void init() {
        indexPage = new IndexPage(driver);
        userNameTestHelper = new UserNameTestHelper(driver, indexPage);
        fieldValidator = new FieldValidator(driver, HOST);
        logout = new Logout(driver);
        registration = new Registration(driver);
    }

    @Test
    public void testTooShortUserName(){
        TooShortUserNameTest.builder()
            .userNameTestHelper(userNameTestHelper)
            .fieldValidator(fieldValidator)
            .indexPage(indexPage)
            .build()
            .testTooShortUserName();
    }

    @Test
    public void testTooLongUserName(){
        TooLongUserNameTest.builder()
            .userNameTestHelper(userNameTestHelper)
            .indexPage(indexPage)
            .fieldValidator(fieldValidator)
            .build()
            .testTooLongUserName();
    }

    @Test
    public void testExistingUserName(){
        ExistingUserNameTest.builder()
            .userNameTestHelper(userNameTestHelper)
            .fieldValidator(fieldValidator)
            .indexPage(indexPage)
            .registration(registration)
            .logout(logout)
            .build()
            .testExistingUserName();
    }
}
