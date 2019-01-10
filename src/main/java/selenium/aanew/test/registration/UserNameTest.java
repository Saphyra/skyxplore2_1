package selenium.aanew.test.registration;

import org.junit.Test;
import selenium.aanew.SeleniumTestApplication;
import selenium.aanew.logic.page.IndexPage;
import selenium.aanew.logic.validator.FieldValidator;
import selenium.aanew.test.registration.username.TooLongUserNameTest;
import selenium.aanew.test.registration.username.TooShortUserNameTest;
import selenium.aanew.test.registration.username.helper.UserNameTestHelper;

import static selenium.aanew.logic.util.LinkUtil.HOST;

public class UserNameTest extends SeleniumTestApplication {
    private UserNameTestHelper userNameTestHelper;
    private IndexPage indexPage;
    private FieldValidator fieldValidator;

    @Override
    protected void init() {
        indexPage = new IndexPage(driver);
        userNameTestHelper = new UserNameTestHelper(driver, indexPage);
        fieldValidator = new FieldValidator(driver, HOST);
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
}
