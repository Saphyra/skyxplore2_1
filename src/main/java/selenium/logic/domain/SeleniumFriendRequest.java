package selenium.logic.domain;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import lombok.RequiredArgsConstructor;
import selenium.logic.validator.NotificationValidator;

@RequiredArgsConstructor
public class SeleniumFriendRequest {
    private static final String SELECTOR_CHARACTER_NAME = "div:first-child";
    private static final String SELECTOR_BUTTON_CONTAINER = "div:last-child > span:first-child";
    private static final String SELECTOR_DECLINE_BUTTON = "button:nth-child(2)";
    private static final String MESSAGE_CODE_FRIEND_REQUEST_DECLINED = "FRIEND_REQUEST_DECLINED";
    private static final String SELECTOR_ACCEPT_BUTTON = "div:last-child > button:last-child";
    private static final String MESSAGE_CODE_FRIEND_REQUEST_ACCEPTED = "FRIEND_REQUEST_ACCEPTED";
    private static final String SELECTOR_BLOCK_BUTTON = "button:first-child";
    private static final String MESSAGE_CODE_CHARACTER_BLOCKED = "CHARACTER_BLOCKED";

    private final WebDriver driver;
    private final WebElement element;
    private final MessageCodes messageCodes;

    public String getCharacterName() {
        return element.findElement(By.cssSelector(SELECTOR_CHARACTER_NAME)).getText();
    }

    public void decline() {
        Actions actions = new Actions(driver);
        actions.moveToElement(getAcceptButton());
        actions.perform();

        WebElement buttonContainer = element.findElement(By.cssSelector(SELECTOR_BUTTON_CONTAINER));
        new WebDriverWait(driver, 2).until(ExpectedConditions.visibilityOf(buttonContainer));
        buttonContainer.findElement(By.cssSelector(SELECTOR_DECLINE_BUTTON)).click();

        new NotificationValidator(driver).verifyNotificationVisibility(messageCodes.get(MESSAGE_CODE_FRIEND_REQUEST_DECLINED));
    }

    private WebElement getAcceptButton() {
        return element.findElement(By.cssSelector(SELECTOR_ACCEPT_BUTTON));
    }

    public void accept() {
        getAcceptButton().click();
        new NotificationValidator(driver).verifyNotificationVisibility(messageCodes.get(MESSAGE_CODE_FRIEND_REQUEST_ACCEPTED));
    }

    public void blockSender() {
        Actions actions = new Actions(driver);
        actions.moveToElement(getAcceptButton());
        actions.perform();

        WebElement buttonContainer = element.findElement(By.cssSelector(SELECTOR_BUTTON_CONTAINER));
        WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
        webDriverWait.until(ExpectedConditions.visibilityOf(buttonContainer));
        buttonContainer.findElement(By.cssSelector(SELECTOR_BLOCK_BUTTON)).click();

        webDriverWait.until(ExpectedConditions.alertIsPresent());
        driver.switchTo().alert().accept();
        new NotificationValidator(driver).verifyNotificationVisibility(messageCodes.get(MESSAGE_CODE_CHARACTER_BLOCKED));
    }
}
