package selenium.page;

import lombok.RequiredArgsConstructor;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

@RequiredArgsConstructor
public class CommunityPage {
    private final WebDriver driver;

    public WebElement getAddFriendButton() {
        return driver.findElement(By.cssSelector("#friends > div:first-of-type"));
    }

    public WebElement getAddFriendContainer() {
        return driver.findElement(By.id("addfriendcontainer"));
    }

    public WebElement getFriendNameInputField() {
        return driver.findElement(By.id("friendname"));
    }

    public List<WebElement> getCharactersCanBeFriendList() {
        return driver.findElements(By.cssSelector("#usersfoundfornewfriend > div.maybefriend"));
    }
}
