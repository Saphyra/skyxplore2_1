package selenium.aanew.domain;

import lombok.RequiredArgsConstructor;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import selenium.aanew.validator.NotificationValidator;

@RequiredArgsConstructor
public class PossibleFriend {
    private final WebElement element;

    public String getCharacterName(){
        return element.findElement(By.cssSelector("div:first-child")).getText();
    }

    public void addFriend(NotificationValidator notificationValidator){
        element.findElement(By.cssSelector("button:first-of-type")).click();
        verifySuccess(notificationValidator);
    }

    private void verifySuccess(NotificationValidator notificationValidator) {
        notificationValidator.verifyNotificationVisibility("Barátkérelem elküldve.");
    }
}
