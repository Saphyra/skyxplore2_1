package com.github.saphyra.selenium.test.account.deleteaccount.helper;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import lombok.RequiredArgsConstructor;
import com.github.saphyra.selenium.logic.domain.SeleniumUser;
import com.github.saphyra.selenium.logic.flow.Navigate;
import com.github.saphyra.selenium.logic.flow.Registration;
import com.github.saphyra.selenium.logic.page.AccountPage;

@RequiredArgsConstructor
public class DeleteAccountTestHelper {
    private final WebDriver driver;
    private final Registration registration;
    private final Navigate navigate;
    private final AccountPage accountPage;

    public SeleniumUser registerAndNavigateToAccount() {
        SeleniumUser user = registration.registerUser();
        navigate.toAccountPage();
        return user;
    }

    public void sendForm() {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.invisibilityOf(accountPage.getInvalidDeleteAccountPasswordField()));
        wait.until(ExpectedConditions.elementToBeClickable(accountPage.getDeleteAccountButton()));

        assertFalse(accountPage.getInvalidDeleteAccountPasswordField().isDisplayed());
        assertTrue(accountPage.getDeleteAccountButton().isEnabled());

        accountPage.getDeleteAccountButton().click();
    }
}
