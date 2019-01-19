package selenium.test.account.changepassword.helper;

import lombok.RequiredArgsConstructor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import selenium.logic.domain.SeleniumUser;
import selenium.logic.flow.Navigate;
import selenium.logic.flow.Registration;
import selenium.logic.page.AccountPage;

import static org.junit.Assert.assertTrue;
import static selenium.logic.util.Util.ATTRIBUTE_VALUE;

@RequiredArgsConstructor
public class ChangePasswordTestHelper {
    private final WebDriver driver;
    private final AccountPage accountPage;
    private final Registration registration;
    private final Navigate navigate;

    public SeleniumUser setUpWithCurrentPassword() {
        SeleniumUser user = registerAndNavigateToAccount();

        accountPage.getCurrentNewPasswordField().sendKeys(user.getPassword());

        return user;
    }

    public SeleniumUser registerAndNavigateToAccount() {
        SeleniumUser user = registration.registerUser();
        navigate.toAccountPage();
        return user;
    }

    public void sendForm() {
        WebDriverWait webDriverWait = new WebDriverWait(driver, 10);
        webDriverWait.until(ExpectedConditions.invisibilityOf(accountPage.getInvalidNewPasswordField()));
        webDriverWait.until(ExpectedConditions.invisibilityOf(accountPage.getInvalidNewConfirmPasswordField()));
        webDriverWait.until(ExpectedConditions.invisibilityOf(accountPage.getCurrentInvalidNewPasswordField()));

        WebElement changePasswordButton = accountPage.getChangePasswordButton();
        assertTrue(changePasswordButton.isEnabled());
        changePasswordButton.click();
    }

    public void verifyInputFieldsAreEmpty(){
        assertTrue(accountPage.getNewPasswordField().getAttribute(ATTRIBUTE_VALUE).isEmpty());
        assertTrue(accountPage.getNewConfirmPasswordField().getAttribute(ATTRIBUTE_VALUE).isEmpty());
        assertTrue(accountPage.getCurrentNewPasswordField().getAttribute(ATTRIBUTE_VALUE).isEmpty());
    }
}
