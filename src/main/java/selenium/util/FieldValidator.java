package selenium.util;

import lombok.RequiredArgsConstructor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.junit.Assert.*;

@RequiredArgsConstructor
public class FieldValidator {
    private final WebDriver driver;
    private final String page;

    public void verifyError(
        WebElement errorField,
        String errorMessage,
        WebElement target,
        WebElement sendButton
    ) {
        verifyError(
            errorField,
            errorMessage,
            target,
            sendButton,
            (WebElement) null
        );
    }

    public void verifyError(
        WebElement errorField,
        String errorMessage,
        WebElement target,
        WebElement sendButton,
        WebElement... inactiveErrorFields
    ) {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.visibilityOf(errorField));
        assertTrue(errorField.isDisplayed());

        assertEquals(errorMessage, errorField.getAttribute("title"));
        verifySendingFormNotPossible(target);

        assertFalse(sendButton.isEnabled());

        for (WebElement inactiveErrorField : inactiveErrorFields) {
            if (inactiveErrorField == null) {
                continue;
            }
            wait.until(ExpectedConditions.invisibilityOf(inactiveErrorField));
            assertFalse(inactiveErrorField.isDisplayed());
        }
    }

    private void verifySendingFormNotPossible(WebElement target) {
        target.sendKeys(Keys.ENTER);
        assertEquals(page, driver.getCurrentUrl());
    }
}
