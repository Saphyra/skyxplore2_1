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
    private final WebDriver driver;
    private final WebElement element;

    public String getCharacterName() {
        return element.findElement(By.cssSelector("div:first-child")).getText();
    }

    public void decline() {
        WebElement acceptButton = getAcceptButton();
        WebElement buttonContainer = element.findElement(By.cssSelector("div:last-child > span:first-child"));

        Actions actions = new Actions(driver);
        actions.moveToElement(acceptButton);
        actions.perform();
        new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOf(buttonContainer));
        buttonContainer.findElement(By.cssSelector("button:nth-child(2)")).click();

        driver.switchTo().alert().accept();
        new NotificationValidator(driver).verifyNotificationVisibility("Barátkérelem elutasítva.");
    }

    private WebElement getAcceptButton() {
        return element.findElement(By.cssSelector("div:last-child > button:last-child"));
    }

    public void accept() {
        getAcceptButton().click();
        new NotificationValidator(driver).verifyNotificationVisibility("Barátkérelem elfogadva.");
    }
}
