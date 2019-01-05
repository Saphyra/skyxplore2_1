package selenium.aanew.test.account.changepassword.helper;

import lombok.RequiredArgsConstructor;
import org.openqa.selenium.WebDriver;
import selenium.aanew.logic.domain.SeleniumUser;
import selenium.aanew.logic.flow.Navigate;
import selenium.aanew.logic.flow.Registration;
import selenium.aanew.logic.page.AccountPage;

import static selenium.aanew.logic.util.DOMUtil.cleanNotifications;

@RequiredArgsConstructor
public class ChangePasswordTestSetup {
    private final WebDriver driver;
    private final AccountPage accountPage;
    private final Registration registration;
    private final Navigate navigate;

    public SeleniumUser setUpForChangePasswordTest() {
        SeleniumUser user = registration.registerUser();
        navigate.toAccountPage();

        clearAll();

        accountPage.getCurrentNewPasswordField().sendKeys(user.getPassword());

        return user;
    }

    private void clearAll() {
        cleanNotifications(driver);

        accountPage.getNewPasswordField().clear();
        accountPage.getNewConfirmPasswordField().clear();
        accountPage.getCurrentNewPasswordField().clear();
    }
}
