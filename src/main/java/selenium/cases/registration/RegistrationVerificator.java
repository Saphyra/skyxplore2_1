package selenium.cases.registration;

import lombok.RequiredArgsConstructor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import selenium.page.IndexPage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static selenium.util.LinkUtil.HOST;

@RequiredArgsConstructor
public class RegistrationVerificator {
    private final WebDriver driver;
    private final IndexPage indexPage;

    public void verifyRegistrationError(WebElement errorField, String errorMessage, WebElement target) {
        assertEquals(errorMessage, errorField.getAttribute("title"));
        verifyRegistrationNotPossible(target);
    }

    private void verifyRegistrationNotPossible(WebElement target) {
        verifyRegistrationButtonDisabled();
        target.sendKeys(Keys.ENTER);
        assertEquals(HOST, driver.getCurrentUrl());
    }

    private void verifyRegistrationButtonDisabled() {
        WebElement registrationButton = indexPage.getRegisterButton();
        assertFalse(registrationButton.isEnabled());
    }
}
