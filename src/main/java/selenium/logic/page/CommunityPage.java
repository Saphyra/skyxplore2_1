package selenium.logic.page;

import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.stream.Collectors;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import lombok.RequiredArgsConstructor;
import selenium.logic.domain.Friend;
import selenium.logic.domain.PossibleFriend;
import selenium.logic.domain.SeleniumFriendRequest;
import selenium.logic.domain.SentFriendRequest;

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
    private static final String SELECTOR_WRITE_MAIL_BUTTON = "#maillistbuttons div:first-child";
    private static final String SELECTOR_ADDRESSEE_INPUT_FIELD = "addressee";
    private static final String SELECTOR_ADDRESSEES = "#addresseelist .addressee";
    private static final String SELECTOR_SUBJECT = "subject";
    private static final String SELECTOR_MESSAGE = "message";
    private static final String SELECTOR_SEND_MAIL_BUTTON = "tr:nth-child(3) button";
    private static final String SELECTOR_MAIL_CONTAINER = "mail";
    private static final String SELECTOR_RECEIVED_MAILS_PAGE_BUTTON = "#maillistbuttons div:nth-child(2)";
    private static final String SELECTOR_RECEIVED_MAILS = "#incomingmaillist .mailitem";
    private static final String SELECTOR_SENT_MAILS_PAGE_BUTTON = "#maillistbuttons div:nth-child(3)";
    private static final String SELECTOR_SENT_MAILS = "#sentmaillist .mailitem";
    private static final String SELECTOR_NUMBER_OF_UNREAD_MAILS = "numberofunreadmails";
    private static final String SELECTOR_ARCHIVED_MAILS_PAGE_BUTTON = "#maillistbuttons div:nth-child(4)";
    private static final String SELECTOR_ARCHIVED_MAILS = "#archivedmaillist .mailitem";
    private static final String SELECTOR_BULK_EDIT_SELECT_INPUT_FIELD_FOR_RECEIVED_MAILS = "actionwithreceivedmails";
    private static final String SELECTOR_EXECUTE_BULK_EDIT_BUTTON_FOR_RECEIVED_MAILS = "#incomingmaillistcontainer .actionwithselectedmails button:last-child";
    private static final String SELECTOR_BULK_RESTORE_INPUT_FIELD = "actionwitharchivedmails";
    private static final String SELECTOR_EXECUTE_BULK_EDIT_BUTTON_FOR_ARCHIVED_MAILS = "#archivedmaillistcontainer .actionwithselectedmails button:last-child";
    private static final String SELECTOR_BULK_DELETE_INPUT_FIELD_FOR_SENT_MAILS = "actionwithdeletedmails";
    private static final String SELECTOR_EXECUTE_BULK_EDIT_BUTTON_FOR_SENT_MAILS = "#sentmaillistcontainer .actionwithselectedmails button:last-child";

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
        return getAddresseeElements().stream()
            .map(WebElement::getText)
            .collect(Collectors.toList());
    }

    public List<WebElement> getAddresseeElements() {
        return driver.findElements(By.cssSelector(SELECTOR_ADDRESSEES));
    }

    public WebElement getMailSubjectField() {
        return driver.findElement(By.id(SELECTOR_SUBJECT));
    }

    public WebElement getMessageField() {
        return driver.findElement(By.id(SELECTOR_MESSAGE));
    }

    public WebElement getSendMailButton() {
        return driver.findElement(By.cssSelector(SELECTOR_SEND_MAIL_BUTTON));
    }

    public WebElement getSendMailContainer() {
        return driver.findElement(By.id(SELECTOR_MAIL_CONTAINER));
    }

    public WebElement getReceivedMailsPageButton() {
        return driver.findElement(By.cssSelector(SELECTOR_RECEIVED_MAILS_PAGE_BUTTON));
    }

    public List<WebElement> getReceivedMails() {
        return driver.findElements(By.cssSelector(SELECTOR_RECEIVED_MAILS));
    }

    public WebElement getSentMailsButton() {
        return driver.findElement(By.cssSelector(SELECTOR_SENT_MAILS_PAGE_BUTTON));
    }

    public List<WebElement> getSentMails() {
        return driver.findElements(By.cssSelector(SELECTOR_SENT_MAILS));
    }

    public WebElement getNumberOfUnreadMails() {
        return driver.findElement(By.id(SELECTOR_NUMBER_OF_UNREAD_MAILS));
    }

    public WebElement getArchivedMailsPageButton() {
        return driver.findElement(By.cssSelector(SELECTOR_ARCHIVED_MAILS_PAGE_BUTTON));
    }

    public List<WebElement> getArchivedMails() {
        return driver.findElements(By.cssSelector(SELECTOR_ARCHIVED_MAILS));
    }

    public WebElement getBulkEditSelectInputFieldForReceivedMails() {
        return driver.findElement(By.id(SELECTOR_BULK_EDIT_SELECT_INPUT_FIELD_FOR_RECEIVED_MAILS));
    }

    public WebElement getExecuteBulkEditButtonForReceivedMails() {
        return driver.findElement(By.cssSelector(SELECTOR_EXECUTE_BULK_EDIT_BUTTON_FOR_RECEIVED_MAILS));
    }

    public WebElement getBulkRestoreInputField() {
        return driver.findElement(By.id(SELECTOR_BULK_RESTORE_INPUT_FIELD));
    }

    public WebElement getExecuteBulkEditButtonForArchivedMails() {
        return driver.findElement(By.cssSelector(SELECTOR_EXECUTE_BULK_EDIT_BUTTON_FOR_ARCHIVED_MAILS));
    }

    public WebElement getBulkDeleteInputFieldForSentMails() {
        return driver.findElement(By.id(SELECTOR_BULK_DELETE_INPUT_FIELD_FOR_SENT_MAILS));
    }

    public WebElement getExecuteBulkEditButtonForSentMails() {
        return driver.findElement(By.cssSelector(SELECTOR_EXECUTE_BULK_EDIT_BUTTON_FOR_SENT_MAILS));
    }
}
