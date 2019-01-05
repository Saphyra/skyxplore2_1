package selenium.aanew.logic.domain;

import lombok.RequiredArgsConstructor;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import selenium.aanew.logic.validator.NotificationValidator;

@RequiredArgsConstructor
public class SentFriendRequest {
    private final WebDriver driver;
    private final WebElement element;

    public String getCharacterName() {
        return element.findElement(By.cssSelector("div:first-child")).getText();
    }

    public void cancel() {
        element.findElement(By.cssSelector("button.friendlistitembutton:first-of-type")).click();
        driver.switchTo().alert().accept();
        new NotificationValidator(driver).verifyNotificationVisibility("Barátkérelem elutasítva.");
    }
}
