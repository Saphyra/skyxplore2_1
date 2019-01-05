package selenium.aanew.test;

import org.junit.Test;
import selenium.aanew.SeleniumTestApplication;
import selenium.aanew.test.account.ChangePasswordTest;

public class AccountTest extends SeleniumTestApplication {
    @Test
    public void testChangePassword() {
        new ChangePasswordTest(driver).runTests();
    }
}
