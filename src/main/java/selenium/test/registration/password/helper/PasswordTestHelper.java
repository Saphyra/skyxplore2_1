package selenium.test.registration.password.helper;

import lombok.RequiredArgsConstructor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import selenium.logic.domain.SeleniumUser;
import selenium.logic.page.IndexPage;

@RequiredArgsConstructor
public class PasswordTestHelper {
    private final WebDriver driver;
    private final IndexPage indexPage;

    public void setUpForPasswordTest(SeleniumUser user) {
        WebElement userNameField = indexPage.getRegistrationUserNameField();
        userNameField.sendKeys(user.getUserName());

        WebElement emailElement = indexPage.getRegistrationEmailField();
        emailElement.sendKeys(user.getEmail());

        WebDriverWait webDriverWait = new WebDriverWait(driver, 10);
        webDriverWait.until(ExpectedConditions.invisibilityOf(indexPage.getInvalidUserNameField()));
        webDriverWait.until(ExpectedConditions.invisibilityOf(indexPage.getInvalidEmailField()));
    }
}
