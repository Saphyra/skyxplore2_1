package selenium.logic.domain;

import lombok.RequiredArgsConstructor;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import selenium.logic.validator.NotificationValidator;

@RequiredArgsConstructor
public class BlockedCharacter {
    private static final String SELECTOR_CHARACTER_NAME = "div:first-child";
    private static final String SELECTOR_BLOCK_BUTTON = ".blockcharacterbutton";
    private static final String NOTIFICATION_CHARACTER_UNBLOCKED = "Blokkolás feloldva.";

    private final WebDriver driver;
    private final WebElement element;

    public String getCharacterName() {
        return element.findElement(By.cssSelector(SELECTOR_CHARACTER_NAME)).getText();
    }

    public void unblock(NotificationValidator notificationValidator) {
        element.findElement(By.cssSelector(SELECTOR_BLOCK_BUTTON)).click();
        driver.switchTo().alert().accept();
        notificationValidator.verifyNotificationVisibility(NOTIFICATION_CHARACTER_UNBLOCKED);
    }
}
