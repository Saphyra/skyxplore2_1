package selenium.util;

import lombok.RequiredArgsConstructor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RequiredArgsConstructor
public class FieldValidator {
    private final WebDriver driver;
    private final String page;

    public void verifyError(WebElement errorField, String errorMessage, WebElement target, WebElement sendButton) {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.visibilityOf(errorField));
        assertTrue(errorField.isDisplayed());

        assertEquals(errorMessage, errorField.getAttribute("title"));
        verifySendingFormNotPossible(target);

        assertFalse(sendButton.isEnabled());
    }

    private void verifySendingFormNotPossible(WebElement target) {
        target.sendKeys(Keys.ENTER);
        assertEquals(page, driver.getCurrentUrl());
    }
}
