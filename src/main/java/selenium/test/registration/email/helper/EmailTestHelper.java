package selenium.test.registration.email.helper;

import lombok.RequiredArgsConstructor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import selenium.logic.domain.SeleniumUser;
import selenium.logic.page.IndexPage;

@RequiredArgsConstructor
public class EmailTestHelper {
    private final WebDriver driver;
    private final IndexPage indexPage;

    public void fillFields(SeleniumUser user) {
        WebElement userNameField = indexPage.getRegistrationUserNameField();
        userNameField.sendKeys(user.getUserName());

        WebElement passwordElement = indexPage.getRegistrationPasswordField();
        passwordElement.sendKeys(user.getPassword());

        WebElement confirmPasswordElement = indexPage.getRegistrationConfirmPasswordField();
        confirmPasswordElement.sendKeys(user.getPassword());

        WebDriverWait webDriverWait = new WebDriverWait(driver, 10);
        webDriverWait.until(ExpectedConditions.invisibilityOf(indexPage.getInvalidUserNameField()));
        webDriverWait.until(ExpectedConditions.invisibilityOf(indexPage.getInvalidPasswordField()));
        webDriverWait.until(ExpectedConditions.invisibilityOf(indexPage.getInvalidConfirmPasswordField()));
    }

}
