package selenium.page;

import lombok.RequiredArgsConstructor;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import selenium.domain.PossibleFriend;
import selenium.domain.SeleniumFriendRequest;
import selenium.domain.SentFriendRequest;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertTrue;

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

    public List<PossibleFriend> getCharactersCanBeFriendList() {
        return driver.findElements(By.cssSelector("#usersfoundfornewfriend > div.maybefriend")).stream()
            .map(PossibleFriend::new)
            .collect(Collectors.toList());
    }

    public WebElement getSentFriendRequestsPageButton() {
        return driver.findElement(By.cssSelector("#listfriends div:first-child div:nth-child(3)"));
    }

    public List<SentFriendRequest> getSentFriendRequests() {
        return driver.findElements(By.cssSelector("#sentfriendrequestitems > div.friendlistitem")).stream()
            .map(element -> new SentFriendRequest(driver, element))
            .collect(Collectors.toList());
    }

    public void closeAddFriendPage() {
        WebElement closeButton = driver.findElement(By.id("addfriendclosebutton"));
        assertTrue(closeButton.isDisplayed());
        closeButton.click();
    }

    public int getNumberOfFriendRequests() {
        return Integer.valueOf(driver.findElement(By.id("friendrequestnum")).getText());
    }

    public WebElement getFriendRequestsPageButton() {
        return driver.findElement(By.cssSelector("#listfriends div:first-child div:nth-child(2)"));
    }

    public List<SeleniumFriendRequest> getFriendRequests() {
        return driver.findElements(By.cssSelector("#friendrequestitems > div.friendlistitem")).stream()
            .map(SeleniumFriendRequest::new)
            .collect(Collectors.toList());
    }

    public WebElement getFriendsPageButton() {
        return driver.findElement(By.cssSelector("#listfriends div:first-child div:nth-child(1)"));
    }
}
