package selenium.logic.page;

import lombok.RequiredArgsConstructor;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import selenium.logic.domain.Friend;
import selenium.logic.domain.PossibleFriend;
import selenium.logic.domain.SeleniumFriendRequest;
import selenium.logic.domain.SentFriendRequest;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertTrue;

@RequiredArgsConstructor
public class CommunityPage {
    private static final String SELECTOR_ADD_FRIEND = "#friends > div:first-of-type";
    private static final String SELECTOR_ADD_FRIEND_CONTAINER = "addfriendcontainer";
    private static final String SELECTOR_ADD_FRIEND_INPUT_FIELD = "friendname";
    private static final String SELECTOR_CHARACTERS_CAN_BE_FRIEND = "#usersfoundfornewfriend > div.maybefriend";
    private static final String SELECTOR_SENT_FRIEND_REQUESTS_PAGE_BUTTON = "#listfriends div:first-child div:nth-child(3)";
    private static final String SELECTOR_SENT_FRIEND_REQUESTS = "#sentfriendrequestitems > div.friendlistitem";
    private static final String SELECTOR_CLOSE_ADD_FRIEND_PAGE_BUTTON = "addfriendclosebutton";
    private static final String SELECTOR_NUMBER_OF_FRIEND_REQUESTS = "friendrequestnum";
    private static final String SELECTOR_FRIEND_REQUESTS_PAGE_BUTTON = "#listfriends div:first-child div:nth-child(2)";
    private static final String SELECTOR_FRIEND_REQUESTS = "#friendrequestitems > div.friendlistitem";
    private static final String SELECTOR_FRIENDS_PAGE_BUTTON = "#listfriends div:first-child div:nth-child(1)";
    private static final String SELECTOR_FRIENDS = "#friendlistitems > div.friendlistitem";
    public static final String SELECTOR_WRITE_MAIL_BUTTON = "#maillistbuttons div:first-child";
    public static final String SELECTOR_ADDRESSEE_INPUT_FIELD = "addressee";

    private final WebDriver driver;

    public WebElement getAddFriendButton() {
        return driver.findElement(By.cssSelector(SELECTOR_ADD_FRIEND));
    }

    public WebElement getAddFriendContainer() {
        return driver.findElement(By.id(SELECTOR_ADD_FRIEND_CONTAINER));
    }

    public WebElement getFriendNameInputField() {
        return driver.findElement(By.id(SELECTOR_ADD_FRIEND_INPUT_FIELD));
    }

    public List<PossibleFriend> getCharactersCanBeFriendList() {
        return driver.findElements(By.cssSelector(SELECTOR_CHARACTERS_CAN_BE_FRIEND)).stream()
            .map(PossibleFriend::new)
            .collect(Collectors.toList());
    }

    public WebElement getSentFriendRequestsPageButton() {
        return driver.findElement(By.cssSelector(SELECTOR_SENT_FRIEND_REQUESTS_PAGE_BUTTON));
    }

    public List<SentFriendRequest> getSentFriendRequests() {
        return driver.findElements(By.cssSelector(SELECTOR_SENT_FRIEND_REQUESTS)).stream()
            .map(element -> new SentFriendRequest(driver, element))
            .collect(Collectors.toList());
    }

    public void closeAddFriendPage() {
        WebElement closeButton = driver.findElement(By.id(SELECTOR_CLOSE_ADD_FRIEND_PAGE_BUTTON));
        assertTrue(closeButton.isDisplayed());
        closeButton.click();
    }

    public int getNumberOfFriendRequests() {
        return Integer.valueOf(driver.findElement(By.id(SELECTOR_NUMBER_OF_FRIEND_REQUESTS)).getText());
    }

    public WebElement getFriendRequestsPageButton() {
        return driver.findElement(By.cssSelector(SELECTOR_FRIEND_REQUESTS_PAGE_BUTTON));
    }

    public List<SeleniumFriendRequest> getFriendRequests() {
        return driver.findElements(By.cssSelector(SELECTOR_FRIEND_REQUESTS)).stream()
            .map(element -> new SeleniumFriendRequest(driver, element))
            .collect(Collectors.toList());
    }

    public WebElement getFriendsPageButton() {
        return driver.findElement(By.cssSelector(SELECTOR_FRIENDS_PAGE_BUTTON));
    }

    public List<Friend> getFriends() {
        return driver.findElements(By.cssSelector(SELECTOR_FRIENDS)).stream()
            .map(Friend::new)
            .collect(Collectors.toList());
    }

    public WebElement getWriteNewMailButton() {
        return driver.findElement(By.cssSelector(SELECTOR_WRITE_MAIL_BUTTON));
    }

    public WebElement getAddresseeInputField() {
        return driver.findElement(By.id(SELECTOR_ADDRESSEE_INPUT_FIELD));
    }

    public List<String> getAddressees() {
        return driver.findElements(By.cssSelector("#addresseelist .addressee")).stream()
            .map(WebElement::getText)
            .collect(Collectors.toList());
    }
}
