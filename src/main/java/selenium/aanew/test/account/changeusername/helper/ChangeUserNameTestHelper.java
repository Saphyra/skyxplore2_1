package selenium.aanew.test.account.changeusername.helper;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import lombok.RequiredArgsConstructor;
import selenium.aanew.logic.domain.SeleniumUser;
import selenium.aanew.logic.flow.Navigate;
import selenium.aanew.logic.flow.Registration;
import selenium.aanew.logic.page.AccountPage;

@RequiredArgsConstructor
public class ChangeUserNameTestHelper {
    private final WebDriver driver;
    private final Registration registration;
    private final AccountPage accountPage;
    private final Navigate navigate;

    public SeleniumUser setUpWithCurrentPassword() {
        SeleniumUser user = registerAndNavigateToAccount();
        accountPage.getChangeUserNamePasswordField().sendKeys(user.getPassword());
        return user;
    }

    public SeleniumUser registerAndNavigateToAccount() {
        SeleniumUser user = registration.registerUser();
        navigate.toAccountPage();
        return user;
    }

    public void setUpWithRandomUserName() {
        registerAndNavigateToAccount();
        accountPage.getChangeUserNameField().sendKeys(SeleniumUser.createRandomUserName());
    }

    public void sendForm() {
        WebDriverWait webDriverWait = new WebDriverWait(driver, 10);
        webDriverWait.until(ExpectedConditions.invisibilityOf(accountPage.getInvalidChangeUserNameField()));
        webDriverWait.until(ExpectedConditions.invisibilityOf(accountPage.getInvalidChangeUserNamePasswordField()));

        assertFalse(accountPage.getInvalidChangeUserNameField().isDisplayed());
        assertFalse(accountPage.getInvalidChangeUserNamePasswordField().isDisplayed());

        WebElement submitButton = accountPage.getChangeUserNameButton();
        assertTrue(submitButton.isEnabled());
        submitButton.click();
    }
}
