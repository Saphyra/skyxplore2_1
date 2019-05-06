package org.github.saphyra.selenium.test.registration;

import static org.github.saphyra.selenium.logic.util.LinkUtil.HOST;

import org.junit.Test;

import org.github.saphyra.selenium.SeleniumTestApplication;
import org.github.saphyra.selenium.logic.flow.Logout;
import org.github.saphyra.selenium.logic.flow.Registration;
import org.github.saphyra.selenium.logic.page.IndexPage;
import org.github.saphyra.selenium.logic.validator.FieldValidator;
import org.github.saphyra.selenium.test.registration.username.ExistingUserNameTest;
import org.github.saphyra.selenium.test.registration.username.TooLongUserNameTest;
import org.github.saphyra.selenium.test.registration.username.TooShortUserNameTest;
import org.github.saphyra.selenium.test.registration.username.helper.UserNameTestHelper;

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
        logout = new Logout(driver, messageCodes);
        registration = new Registration(driver, messageCodes);
    }

    @Test
    public void testTooShortUserName() {
        TooShortUserNameTest.builder()
            .userNameTestHelper(userNameTestHelper)
            .fieldValidator(fieldValidator)
            .indexPage(indexPage)
            .messageCodes(messageCodes)
            .build()
            .testTooShortUserName();
    }

    @Test
    public void testTooLongUserName() {
        TooLongUserNameTest.builder()
            .userNameTestHelper(userNameTestHelper)
            .indexPage(indexPage)
            .fieldValidator(fieldValidator)
            .messageCodes(messageCodes)
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
            .messageCodes(messageCodes)
            .build()
            .testExistingUserName();
    }
}
