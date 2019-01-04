package selenium.cases.community.testcase;

import lombok.Builder;
import org.openqa.selenium.WebElement;
import selenium.domain.PossibleFriend;
import selenium.domain.SeleniumAccount;
import selenium.domain.SeleniumFriendRequest;
import selenium.domain.SentFriendRequest;
import selenium.domain.SeleniumCharacter;
import selenium.flow.Login;
import selenium.flow.Navigate;
import selenium.flow.SelectCharacter;
import selenium.page.CommunityPage;
import selenium.page.OverviewPage;
import selenium.validator.NotificationValidator;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static selenium.domain.SeleniumCharacter.CHARACTER_NAME_PREFIX;

@Builder
public class FriendshipTest {
    private final NotificationValidator notificationValidator;
    private final Supplier<SeleniumAccount> seleniumAccountSupplier;
    private final Navigate navigate;
    private final SelectCharacter selectCharacter;
    private final Login login;
    private final CommunityPage communityPage;
    private final OverviewPage overviewPage;

    public FriendshipTest testFilter() {
        SeleniumAccount account1 = seleniumAccountSupplier.get();
        SeleniumAccount account2 = seleniumAccountSupplier.get();

        goToCommunityPageOf(account1, account1.getCharacter1());

        searchForPossibleFriends(CHARACTER_NAME_PREFIX);
        verifySearchResult(account1.getCharacters(), account2.getCharacters());

        searchForPossibleFriends(account2.getCharacter1().getCharacterName());
        verifySearchResult(
            Stream.concat(account1.getCharacters().stream(), Stream.of(account2.getCharacter2())).collect(Collectors.toList()),
            Arrays.asList(account2.getCharacter1())
        );

        return this;
    }

    public FriendshipTest testSendFriendRequest() {
        SeleniumAccount account1 = seleniumAccountSupplier.get();
        SeleniumAccount account2 = seleniumAccountSupplier.get();

        goToCommunityPageOf(account1, account1.getCharacter1());

        sendFriendRequestTo(account2.getCharacter1());

        verifyCannotSendFriendRequest(account2.getCharacter1());
        communityPage.closeAddFriendPage();
        verifyFriendRequestInList(account2.getCharacter1());

        goToCommunityPageOf(account2, account2.getCharacter1(), 1);

        verifyFriendRequestNotifications(1);

        searchForPossibleFriends(account1.getCharacter1().getCharacterName());
        verifySearchResult(Arrays.asList(account1.getCharacter1()), Collections.emptyList());

        communityPage.closeAddFriendPage();
        verifyFriendRequestArrived(account1.getCharacter1());

        return this;
    }

    public FriendshipTest testCancelFriendRequest() {
        SeleniumAccount account1 = seleniumAccountSupplier.get();
        SeleniumAccount account2 = seleniumAccountSupplier.get();

        goToCommunityPageOf(account1, account1.getCharacter1());

        sendFriendRequestTo(account2.getCharacter1());

        communityPage.getSentFriendRequestsPageButton().click();

        SentFriendRequest friendRequest = communityPage.getSentFriendRequests().stream()
            .filter(sentFriendRequest -> sentFriendRequest.getCharacterName().equals(account2.getCharacter1().getCharacterName()))
            .findFirst()
            .orElseThrow(() -> new RuntimeException("SentFriendRequest not found."));

        friendRequest.cancel();

        communityPage.getFriendsPageButton().click();
        searchForPossibleFriends(account2.getCharacter1().getCharacterName());
        verifySearchResult(Collections.emptyList(), Arrays.asList(account2.getCharacter1()));

        goToCommunityPageOf(account2, account2.getCharacter1(), 0);
        verifyFriendRequestNotifications(0);

        communityPage.getFriendsPageButton().click();
        searchForPossibleFriends(account1.getCharacter1().getCharacterName());
        verifySearchResult(Collections.emptyList(), Arrays.asList(account1.getCharacter1()));

        return this;
    }

    public FriendshipTest testAcceptFriendRequest() {
        return this;
    }

    public FriendshipTest testDeclineFriendRequest() {
        return this;
    }

    private void goToCommunityPageOf(SeleniumAccount account, SeleniumCharacter character) {
        goToCommunityPageOf(account, character, 0);
    }

    private void goToCommunityPageOf(SeleniumAccount account, SeleniumCharacter character, int numberOfNotifications) {
        login.login(account.getUser());
        selectCharacter.selectCharacter(character);
        verifyNotificationNum(numberOfNotifications);
        navigate.toCommunityPage();
    }

    private void verifyNotificationNum(int numberOfNotifications) {
        WebElement notificationElement = overviewPage.getNotificationNumberElement();
        String notificationText = notificationElement.getText();
        int displayedNumber = 0;
        if (!notificationText.isEmpty()) {
            displayedNumber = Integer.valueOf(notificationText.split("\\(")[1].split("\\)")[0]);
        }
        assertEquals(numberOfNotifications, displayedNumber);
    }

    private void sendFriendRequestTo(SeleniumCharacter character) {
        searchForPossibleFriends(character.getCharacterName());

        communityPage.getCharactersCanBeFriendList().stream()
            .filter(p -> p.getCharacterName().equals(character.getCharacterName()))
            .findFirst()
            .orElseThrow(() -> new RuntimeException("Friend not found in search result."))
            .addFriend(notificationValidator);
    }

    private void searchForPossibleFriends(String characterName) {
        if (!communityPage.getAddFriendContainer().isDisplayed()) {
            openAddFriendPage();
        }

        WebElement friendNameInputField = communityPage.getFriendNameInputField();
        friendNameInputField.clear();
        friendNameInputField.sendKeys(characterName);
    }

    private void openAddFriendPage() {
        communityPage.getAddFriendButton().click();
        assertTrue(communityPage.getAddFriendContainer().isDisplayed());
    }

    private void verifyCannotSendFriendRequest(SeleniumCharacter character) {
        searchForPossibleFriends(character.getCharacterName());
        verifySearchResult(
            Arrays.asList(character),
            Collections.emptyList()
        );
    }

    private void verifySearchResult(List<SeleniumCharacter> shouldNotContain, List<SeleniumCharacter> shouldContain) {
        List<PossibleFriend> searchResult = communityPage.getCharactersCanBeFriendList();
        List<String> characterNames = searchResult.stream()
            .map(PossibleFriend::getCharacterName)
            .collect(Collectors.toList());

        shouldNotContain.forEach(character -> assertTrue(characterNames.stream().noneMatch(s -> s.equals(character.getCharacterName()))));
        shouldContain.forEach(character -> assertTrue(characterNames.stream().anyMatch(s -> s.equals(character.getCharacterName()))));
    }

    private void verifyFriendRequestNotifications(int numberOfWantedNotifications) {
        assertEquals(numberOfWantedNotifications, communityPage.getNumberOfFriendRequests());
    }

    private void verifyFriendRequestInList(SeleniumCharacter character) {
        communityPage.getSentFriendRequestsPageButton().click();
        assertTrue(
            communityPage.getSentFriendRequests().stream()
                .anyMatch(sentFriendRequest -> sentFriendRequest.getCharacterName().equals(character.getCharacterName()))
        );
    }

    private void verifyFriendRequestArrived(SeleniumCharacter character) {
        communityPage.getFriendRequestsPageButton().click();
        List<SeleniumFriendRequest> friendRequests = communityPage.getFriendRequests();
        assertTrue(
            friendRequests.stream()
                .anyMatch(seleniumFriendRequest -> seleniumFriendRequest.getCharacterName().equals(character.getCharacterName()))
        );
    }
}
