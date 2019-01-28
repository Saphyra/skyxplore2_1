package selenium.logic.validator;

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
    private static final String ATTRIBUTE_TITLE = "title";

    private final WebDriver driver;
    private final String page;

    public void verifySuccess(WebElement errorField, WebElement button) {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.invisibilityOf(errorField));
        wait.until(ExpectedConditions.elementToBeClickable(button));
        assertFalse(errorField.isDisplayed());
        assertTrue(button.isEnabled());
    }

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

        assertEquals(errorMessage, errorField.getAttribute(ATTRIBUTE_TITLE));
        assertFalse(sendButton.isEnabled());
        verifySendingFormNotPossible(target, sendButton);

        for (WebElement inactiveErrorField : inactiveErrorFields) {
            if (inactiveErrorField == null) {
                break;
            }
            wait.until(ExpectedConditions.invisibilityOf(inactiveErrorField));
            assertFalse(inactiveErrorField.isDisplayed());
        }
    }

    private void verifySendingFormNotPossible(WebElement target, WebElement sendButton) {
        target.sendKeys(Keys.ENTER);
        sendButton.click();
        assertEquals(page, driver.getCurrentUrl());
    }
}
