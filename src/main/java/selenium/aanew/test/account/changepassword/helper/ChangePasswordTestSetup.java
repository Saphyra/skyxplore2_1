package selenium.aanew.test.account.changepassword.helper;

import lombok.RequiredArgsConstructor;
import org.openqa.selenium.WebDriver;
import selenium.aanew.logic.domain.SeleniumUser;
import selenium.aanew.logic.page.AccountPage;

import static selenium.aanew.logic.util.DOMUtil.cleanNotifications;

@RequiredArgsConstructor
public class ChangePasswordTestSetup {
    private final WebDriver driver;
    private final AccountPage accountPage;

    public void setUpForChangePasswordTest(SeleniumUser user) {
        clearAll();

        accountPage.getCurrentNewPasswordField().sendKeys(user.getPassword());
    }

    private void clearAll() {
        cleanNotifications(driver);

        accountPage.getNewPasswordField().clear();
        accountPage.getNewConfirmPasswordField().clear();
        accountPage.getCurrentNewPasswordField().clear();
    }
}
