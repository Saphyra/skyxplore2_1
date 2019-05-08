package com.github.saphyra.selenium.test.account.changeusername.helper;

import lombok.RequiredArgsConstructor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import com.github.saphyra.selenium.logic.domain.SeleniumUser;
import com.github.saphyra.selenium.logic.flow.Navigate;
import com.github.saphyra.selenium.logic.flow.Registration;
import com.github.saphyra.selenium.logic.page.AccountPage;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

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
        WebElement submitButton = accountPage.getChangeUserNameButton();
        WebDriverWait webDriverWait = new WebDriverWait(driver, 10);
        webDriverWait.until(ExpectedConditions.invisibilityOf(accountPage.getInvalidChangeUserNameField()));
        webDriverWait.until(ExpectedConditions.invisibilityOf(accountPage.getInvalidChangeUserNamePasswordField()));
        webDriverWait.until(ExpectedConditions.elementToBeClickable(submitButton));

        assertFalse(accountPage.getInvalidChangeUserNameField().isDisplayed());
        assertFalse(accountPage.getInvalidChangeUserNamePasswordField().isDisplayed());


        assertTrue(submitButton.isEnabled());
        submitButton.click();
    }
}
