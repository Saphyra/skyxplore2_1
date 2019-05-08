package com.github.saphyra.selenium.logic.page;

import com.github.saphyra.selenium.logic.domain.SentFriendRequest;
import com.github.saphyra.selenium.logic.domain.localization.MessageCodes;
import lombok.RequiredArgsConstructor;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import com.github.saphyra.selenium.logic.domain.Friend;
import com.github.saphyra.selenium.logic.domain.PossibleFriend;
import com.github.saphyra.selenium.logic.domain.SeleniumFriendRequest;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.Assert.assertTrue;
import static com.github.saphyra.selenium.logic.util.WaitUtil.getListWithWait;
import static com.github.saphyra.selenium.logic.util.WaitUtil.getWithWait;

@RequiredArgsConstructor
public class CommunityPage {
    private static final String SELECTOR_BLOCKED_CHARACTERS_CONTAINER = "blocked-characters-container";
    private static final String SELECTOR_ADD_FRIEND = "#add-friend-button";
    private static final String SELECTOR_ADD_FRIEND_CONTAINER = "main-add-friend";
    private static final String SELECTOR_ADD_FRIEND_INPUT_FIELD = "friend-name";
    private static final String SELECTOR_ADD_FRIEND_SEARCH_RESULT = ".character-can-be-friend";
    private static final String SELECTOR_SENT_FRIEND_REQUESTS_PAGE_BUTTON = "#sent-friend-requests-tab-button";
    private static final String SELECTOR_SENT_FRIEND_REQUESTS = "#sent-friend-request-list > .friend-list-item";
    private static final String SELECTOR_CLOSE_ADD_FRIEND_PAGE_BUTTON = "add-friend-close-button";
    private static final String SELECTOR_NUMBER_OF_FRIEND_REQUESTS = "friend-request-num";
    private static final String SELECTOR_FRIEND_REQUESTS_PAGE_BUTTON = "#friend-requests-tab-button";
    private static final String SELECTOR_FRIEND_REQUESTS = "#friend-request-list > .friend-list-item";
    private static final String SELECTOR_FRIENDS_PAGE_BUTTON = "#friends-tab-button";
    private static final String SELECTOR_FRIENDS = "#friend-list > .friend-list-item";
    private static final String SELECTOR_WRITE_MAIL_BUTTON = "#write-mail-button";
    private static final String SELECTOR_ADDRESSEE_INPUT_FIELD = "addressee";
    private static final String SELECTOR_ADDRESSEES = "#addressee-search-result .addressee";
    private static final String SELECTOR_SUBJECT = "subject";
    private static final String SELECTOR_MESSAGE = "message";
    private static final String SELECTOR_SEND_MAIL_BUTTON = "tr:nth-child(3) button";
    private static final String SELECTOR_MAIL_CONTAINER = "main-write-mail";
    private static final String SELECTOR_INCOMING_MAILS_PAGE_BUTTON = "#incoming-mails-button";
    private static final String SELECTOR_INCOMING_MAILS = "#incoming-mail-list .mail-item";
    private static final String SELECTOR_SENT_MAILS_PAGE_BUTTON = "#sent-mails-button";
    private static final String SELECTOR_SENT_MAILS = "#sent-mail-list .mail-item";
    private static final String SELECTOR_NUMBER_OF_UNREAD_MAILS = "number-of-unread-mails";
    private static final String SELECTOR_ARCHIVED_MAILS_PAGE_BUTTON = "#archived-mails-button";
    private static final String SELECTOR_ARCHIVED_MAILS = "#archived-mail-list .mail-item";
    private static final String SELECTOR_BULK_EDIT_SELECT_INPUT_FIELD_FOR_INCOMING_MAILS = "action-with-incoming-mails";
    private static final String SELECTOR_EXECUTE_BULK_EDIT_BUTTON_FOR_INCOMING_MAILS = "#process-action-with-incoming-mails-button";
    private static final String SELECTOR_BULK_RESTORE_INPUT_FIELD = "action-with-archived-mails";
    private static final String SELECTOR_EXECUTE_BULK_EDIT_BUTTON_FOR_ARCHIVED_MAILS = "#process-action-with-archived-mails-button";
    private static final String SELECTOR_BULK_DELETE_INPUT_FIELD_FOR_SENT_MAILS = "action-with-sent-mails";
    private static final String SELECTOR_EXECUTE_BULK_EDIT_BUTTON_FOR_SENT_MAILS = "#process-action-with-sent-mails-button";
    private static final String SELECTOR_BLOCK_CHARACTERS_PAGE_BUTTON = "#blocked-characters-page-button";
    private static final String SELECTOR_BLOC_CHARACTER_WINDOW_BUTTON = "#block-character-button";
    private static final String SELECTOR_BLOCK_CHARACTER_NAME_INPUT_FIELD = "block-character-name";
    private static final String SELECTOR_BLOCKABLE_CHARACTERS = "#block-character-search-result .character-can-be-blocked";
    private static final String SELECTOR_FRIENDS_MAIN_PAGE_BUTTON = "#friends-page-button";
    private static final String SELECTOR_BLOCKED_CHARACTERS = "#blocked-character-list .friend-list-item";
    private static final String SELECTOR_SELECT_ALL_INCOMING_MAIL_BUTTON = "select-all-incoming-mail-button";
    private static final String SELECTOR_SELECT_ALL_ARCHIVED_MAIL_BUTTON = "select-all-archived-mail-button";
    private static final String SELECTOR_SELECT_ALL_SENT_MAILS_BUTTON = "select-all-sent-mail-button";
    private static final String SELECTOR_BLOCK_CHARACTER_WINDOW = "main-block-character";

    private final WebDriver driver;
    private final MessageCodes messageCodes;

    public WebElement getAddFriendButton() {
        return driver.findElement(By.cssSelector(SELECTOR_ADD_FRIEND));
    }

    public WebElement getAddFriendContainer() {
        return driver.findElement(By.id(SELECTOR_ADD_FRIEND_CONTAINER));
    }

    public WebElement getFriendNameInputField() {
        return driver.findElement(By.id(SELECTOR_ADD_FRIEND_INPUT_FIELD));
    }

    public List<PossibleFriend> getAddFriendSearchResult() {
        return getListWithWait(() -> driver.findElements(By.cssSelector(SELECTOR_ADD_FRIEND_SEARCH_RESULT)), "Querying possible friends...")
            .stream()
            .map(element -> new PossibleFriend(element, messageCodes))
            .collect(Collectors.toList());
    }

    public WebElement getSentFriendRequestsPageButton() {
        return driver.findElement(By.cssSelector(SELECTOR_SENT_FRIEND_REQUESTS_PAGE_BUTTON));
    }

    public List<SentFriendRequest> getSentFriendRequests() {
        return getListWithWait(
            () -> driver.findElements(By.cssSelector(SELECTOR_SENT_FRIEND_REQUESTS)).stream()
                .map(element -> new SentFriendRequest(driver, element, messageCodes))
                .collect(Collectors.toList()),
            "Querying sent friend requests"
        );
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
        return getListWithWait(
            () -> driver.findElements(By.cssSelector(SELECTOR_FRIEND_REQUESTS)).stream()
                .map(element -> new SeleniumFriendRequest(driver, element, messageCodes))
                .collect(Collectors.toList()),
            "Querying friend requests...");
    }

    public WebElement getOpenFriendsPageButton() {
        return driver.findElement(By.cssSelector(SELECTOR_FRIENDS_PAGE_BUTTON));
    }

    public List<Friend> getFriends() {
        return getListWithWait(
            () -> driver.findElements(By.cssSelector(SELECTOR_FRIENDS)).stream()
                .map(element -> new Friend(driver, element))
                .collect(Collectors.toList()),
            "Querying friends..."
        );
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
        return getWithWait(() -> {
            List<WebElement> result = driver.findElements(By.cssSelector(SELECTOR_ADDRESSEES));
            return result.isEmpty() ? Optional.empty() : Optional.of(result);
        }, "Querying addressee elements...").orElse(Collections.emptyList());
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

    public WebElement getIncomingMailsPageButton() {
        return driver.findElement(By.cssSelector(SELECTOR_INCOMING_MAILS_PAGE_BUTTON));
    }

    public boolean isIncomingMailExists() {
        return driver.findElements(By.cssSelector(SELECTOR_INCOMING_MAILS)).size() > 0;
    }

    public boolean isArchivedMailExists() {
        return driver.findElements(By.cssSelector(SELECTOR_ARCHIVED_MAILS)).size() > 0;
    }

    public boolean isSentMailExists() {
        return driver.findElements(By.cssSelector(SELECTOR_SENT_MAILS)).size() > 0;
    }

    public List<WebElement> getIncomingMails(boolean canBeEmpty) {
        if (canBeEmpty) {
            return driver.findElements(By.cssSelector(SELECTOR_INCOMING_MAILS));
        }

        return getIncomingMails();
    }

    public List<WebElement> getIncomingMails() {
        return getWithWait(() -> {
            List<WebElement> result = driver.findElements(By.cssSelector(SELECTOR_INCOMING_MAILS));
            return result.isEmpty() ? Optional.empty() : Optional.of(result);
        }, "Querying incoming mails...").orElse(Collections.emptyList());
    }

    public WebElement getSentMailsPageButton() {
        return driver.findElement(By.cssSelector(SELECTOR_SENT_MAILS_PAGE_BUTTON));
    }

    public List<WebElement> getSentMails() {
        return getWithWait(() -> {
            List<WebElement> result = driver.findElements(By.cssSelector(SELECTOR_SENT_MAILS));
            return result.isEmpty() ? Optional.empty() : Optional.of(result);
        }, "Querying sent mails...").orElse(Collections.emptyList());
    }

    public WebElement getNumberOfUnreadMails() {
        return driver.findElement(By.id(SELECTOR_NUMBER_OF_UNREAD_MAILS));
    }

    public WebElement getArchivedMailsPageButton() {
        return driver.findElement(By.cssSelector(SELECTOR_ARCHIVED_MAILS_PAGE_BUTTON));
    }

    public List<WebElement> getArchivedMails() {
        return getWithWait(() -> {
            List<WebElement> result = driver.findElements(By.cssSelector(SELECTOR_ARCHIVED_MAILS));
            return result.isEmpty() ? Optional.empty() : Optional.of(result);
        }, "Querying archived mails").orElse(Collections.emptyList());
    }

    public WebElement getBulkEditInputFieldForIncomingMails() {
        return driver.findElement(By.id(SELECTOR_BULK_EDIT_SELECT_INPUT_FIELD_FOR_INCOMING_MAILS));
    }

    public WebElement getExecuteBulkEditButtonForReceivedMails() {
        return driver.findElement(By.cssSelector(SELECTOR_EXECUTE_BULK_EDIT_BUTTON_FOR_INCOMING_MAILS));
    }

    public WebElement getBulkEditInputFieldForArchivedMails() {
        return driver.findElement(By.id(SELECTOR_BULK_RESTORE_INPUT_FIELD));
    }

    public WebElement getExecuteBulkEditButtonForArchivedMails() {
        return driver.findElement(By.cssSelector(SELECTOR_EXECUTE_BULK_EDIT_BUTTON_FOR_ARCHIVED_MAILS));
    }

    public WebElement getBulkEditInputFieldForSentMails() {
        return driver.findElement(By.id(SELECTOR_BULK_DELETE_INPUT_FIELD_FOR_SENT_MAILS));
    }

    public WebElement getExecuteBulkEditButtonForSentMails() {
        return driver.findElement(By.cssSelector(SELECTOR_EXECUTE_BULK_EDIT_BUTTON_FOR_SENT_MAILS));
    }

    public WebElement getBlockCharactersPageButton() {
        return driver.findElement(By.cssSelector(SELECTOR_BLOCK_CHARACTERS_PAGE_BUTTON));
    }

    public WebElement getBlockCharacterWindowButton() {
        return driver.findElement(By.cssSelector(SELECTOR_BLOC_CHARACTER_WINDOW_BUTTON));
    }

    public WebElement getBlockCharacterNameInputField() {
        return driver.findElement(By.id(SELECTOR_BLOCK_CHARACTER_NAME_INPUT_FIELD));
    }

    public List<WebElement> getCharactersCanBeBlocked() {
        return getListWithWait(
            () -> driver.findElements(By.cssSelector(SELECTOR_BLOCKABLE_CHARACTERS)),
            "Querying characters can be blocked");
    }

    public WebElement getFriendsMainPageButton() {
        return driver.findElement(By.cssSelector(SELECTOR_FRIENDS_MAIN_PAGE_BUTTON));
    }

    public List<WebElement> getBlockedCharacters() {
        return getListWithWait(
            () -> driver.findElements(By.cssSelector(SELECTOR_BLOCKED_CHARACTERS)),
            "Querying blocked characters"
        );
    }

    public WebElement getSelectAllIncomingMailsButton() {
        return driver.findElement(By.id(SELECTOR_SELECT_ALL_INCOMING_MAIL_BUTTON));
    }

    public WebElement getSelectAllArchivedMailsButton() {
        return driver.findElement(By.id(SELECTOR_SELECT_ALL_ARCHIVED_MAIL_BUTTON));
    }

    public WebElement getSelectAllSentMailsButton() {
        return driver.findElement(By.id(SELECTOR_SELECT_ALL_SENT_MAILS_BUTTON));
    }

    public WebElement getBlockedCharactersContainer() {
        return driver.findElement(By.id(SELECTOR_BLOCKED_CHARACTERS_CONTAINER));
    }

    public WebElement getBlockCharacterWindow() {
        return driver.findElement(By.id(SELECTOR_BLOCK_CHARACTER_WINDOW));
    }
}
