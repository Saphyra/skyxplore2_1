package selenium.aanew.test.account.changepassword.helper;

import lombok.RequiredArgsConstructor;
import org.openqa.selenium.WebDriver;
import selenium.aanew.logic.domain.SeleniumUser;

import static selenium.aanew.logic.util.DOMUtil.cleanNotifications;

@RequiredArgsConstructor
public class ChangePasswordTestSetup {
    private final WebDriver driver;
    //TODO import fix
    private final selenium.page.AccountPage accountPage;

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
