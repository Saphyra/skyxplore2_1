package com.github.saphyra.selenium.test.registration;

import com.github.saphyra.selenium.SeleniumTestApplication;
import com.github.saphyra.selenium.logic.flow.Logout;
import com.github.saphyra.selenium.logic.flow.Registration;
import com.github.saphyra.selenium.logic.page.IndexPage;
import com.github.saphyra.selenium.logic.validator.FieldValidator;
import com.github.saphyra.selenium.test.registration.username.ExistingUserNameTest;
import com.github.saphyra.selenium.test.registration.username.TooLongUserNameTest;
import com.github.saphyra.selenium.test.registration.username.TooShortUserNameTest;
import com.github.saphyra.selenium.test.registration.username.helper.UserNameTestHelper;
import org.junit.Test;

import static com.github.saphyra.selenium.logic.util.LinkUtil.INDEX_PAGE;

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
        fieldValidator = new FieldValidator(driver, INDEX_PAGE);
        logout = new Logout(driver);
        registration = new Registration(driver);
    }

    @Test
    public void testTooShortUserName() {
        TooShortUserNameTest.builder()
            .userNameTestHelper(userNameTestHelper)
            .fieldValidator(fieldValidator)
            .indexPage(indexPage)
            .build()
            .testTooShortUserName();
    }

    @Test
    public void testTooLongUserName() {
        TooLongUserNameTest.builder()
            .userNameTestHelper(userNameTestHelper)
            .indexPage(indexPage)
            .fieldValidator(fieldValidator)
            .build()
            .testTooLongUserName();
    }

    @Test
    public void testExistingUserName() {
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
