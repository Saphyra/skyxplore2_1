package org.github.saphyra.selenium.logic.domain;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class Friend {
    private static final String SELECTOR_CHARACTER_NAME = "div:first-child";
    private static final String SELECTOR_WRITE_MAIL_BUTTON = "div:last-child > button:last-child";
    private static final String SELECTOR_BUTTON_CONTAINER = "div:last-child > span:first-child";
    private static final String SELECTOR_DELETE_BUTTON = "button:first-child";

    private final WebDriver driver;
    private final WebElement element;

    public String getCharacterName() {
        return element.findElement(By.cssSelector(SELECTOR_CHARACTER_NAME)).getText();
    }

    public void delete() {
        Actions actions = new Actions(driver);
        actions.moveToElement(getWriteMailButton());
        actions.perform();

        WebElement buttonContainer = element.findElement(By.cssSelector(SELECTOR_BUTTON_CONTAINER));
        WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
        webDriverWait.until(ExpectedConditions.visibilityOf(buttonContainer));
        buttonContainer.findElement(By.cssSelector(SELECTOR_DELETE_BUTTON)).click();
        webDriverWait.until(ExpectedConditions.alertIsPresent());
        driver.switchTo().alert().accept();
    }

    public void sendMail() {
        getWriteMailButton().click();
    }

    private WebElement getWriteMailButton() {
        return element.findElement(By.cssSelector(SELECTOR_WRITE_MAIL_BUTTON));
    }

}
