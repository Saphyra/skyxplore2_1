package selenium.aanew.logic.domain;

import lombok.RequiredArgsConstructor;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import selenium.aanew.logic.validator.NotificationValidator;

@RequiredArgsConstructor
public class SeleniumFriendRequest {
    private static final String SELECTOR_CHARACTER_NAME = "div:first-child";
    private static final String SELECTOR_BUTTON_CONTAINER = "div:last-child > span:first-child";
    private static final String SELECTOR_DECLINE_BUTTON = "button:nth-child(2)";
    private static final String NOTIFICATION_FRIEND_REQUEST_DECLINED = "Barátkérelem elutasítva.";
    private static final String SELECTOR_ACCEPT_BUTTON = "div:last-child > button:last-child";
    private static final String NOTIFICATION_FRIEND_REQUEST_ACCEPTED = "Barátkérelem elfogadva.";

    private final WebDriver driver;
    private final WebElement element;

    public String getCharacterName() {
        return element.findElement(By.cssSelector(SELECTOR_CHARACTER_NAME)).getText();
    }

    public void decline() {
        WebElement acceptButton = getAcceptButton();
        WebElement buttonContainer = element.findElement(By.cssSelector(SELECTOR_BUTTON_CONTAINER));

        Actions actions = new Actions(driver);
        actions.moveToElement(acceptButton);
        actions.perform();
        new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOf(buttonContainer));
        buttonContainer.findElement(By.cssSelector(SELECTOR_DECLINE_BUTTON)).click();

        driver.switchTo().alert().accept();
        new NotificationValidator(driver).verifyNotificationVisibility(NOTIFICATION_FRIEND_REQUEST_DECLINED);
    }

    private WebElement getAcceptButton() {
        return element.findElement(By.cssSelector(SELECTOR_ACCEPT_BUTTON));
    }

    public void accept() {
        getAcceptButton().click();
        new NotificationValidator(driver).verifyNotificationVisibility(NOTIFICATION_FRIEND_REQUEST_ACCEPTED);
    }
}
