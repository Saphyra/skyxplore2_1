package com.github.saphyra.selenium.test.account.changeemail.helper;

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
public class ChangeEmailTestHelper {
    private final AccountPage accountPage;
    private final Registration registration;
    private final Navigate navigate;
    private final WebDriver driver;

    public SeleniumUser registerAndNavigateToAccount() {
        SeleniumUser user = registration.registerUser();
        navigate.toAccountPage();
        return user;
    }

    public SeleniumUser setUpWithCurrentPassword() {
        SeleniumUser user = registerAndNavigateToAccount();
        accountPage.getChangeEmailPasswordField().sendKeys(user.getPassword());
        return user;
    }

    @SuppressWarnings("UnusedReturnValue")
    public SeleniumUser setUpWithRandomEmail() {
        SeleniumUser user = registerAndNavigateToAccount();
        accountPage.getChangeEmailField().sendKeys(SeleniumUser.createRandomEmail());
        return user;
    }

    public void submitForm() {
        WebElement submitButton = accountPage.getChangeEmailButton();
        WebDriverWait webDriverWait = new WebDriverWait(driver, 10);
        webDriverWait.until(ExpectedConditions.invisibilityOf(accountPage.getInvalidChangeEmailField()));
        webDriverWait.until(ExpectedConditions.invisibilityOf(accountPage.getInvalidChangeEmailPasswordField()));
        webDriverWait.until(ExpectedConditions.elementToBeClickable(submitButton));

        assertFalse(accountPage.getInvalidChangeEmailField().isDisplayed());
        assertFalse(accountPage.getInvalidChangeEmailPasswordField().isDisplayed());

        assertTrue(submitButton.isEnabled());
        submitButton.click();
    }
}
