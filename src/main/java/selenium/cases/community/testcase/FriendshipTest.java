package selenium.cases.community.testcase;

import lombok.Builder;
import org.openqa.selenium.WebElement;
import selenium.cases.community.domain.PossibleFriend;
import selenium.cases.community.domain.SeleniumAccount;
import selenium.domain.SeleniumCharacter;
import selenium.flow.Login;
import selenium.flow.Navigate;
import selenium.flow.SelectCharacter;
import selenium.page.CommunityPage;
import selenium.validator.NotificationValidator;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

    public FriendshipTest testFilter() {
        SeleniumAccount account1 = seleniumAccountSupplier.get();
        SeleniumAccount account2 = seleniumAccountSupplier.get();

        goToCommunityPageOf(account1);
        openAddFriendPage();

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

        goToCommunityPageOf(account1);

        sendFriendRequestTo(account2.getCharacter1());

        verifyCannotSendFriendRequest(account2.getCharacter1());
        communityPage.closeAddFriendPage();
        verifyFriendRequestInList(account2.getCharacter1());

        return this;
    }

    private void verifyFriendRequestInList(SeleniumCharacter character) {
        communityPage.getSentFriendRequestsPageButton().click();
        assertTrue(
            communityPage.getSentFriendRequests().stream()
                .anyMatch(sentFriendRequest -> sentFriendRequest.getCharacterName().equals(character.getCharacterName()))
        );

    }

    public FriendshipTest testCancelFriendRequest() {
        return this;
    }

    public FriendshipTest testAcceptFriendRequest() {
        return this;
    }

    private void goToCommunityPageOf(SeleniumAccount account1) {
        login.login(account1.getUser());
        selectCharacter.selectCharacter(account1.getCharacter1());
        navigate.toCommunityPage();
    }

    private void openAddFriendPage() {
        communityPage.getAddFriendButton().click();
        assertTrue(communityPage.getAddFriendContainer().isDisplayed());
    }

    private void sendFriendRequestTo(SeleniumCharacter character) {
        openAddFriendPage();
        searchForPossibleFriends(character.getCharacterName());

        communityPage.getCharactersCanBeFriendList().stream()
            .filter(p -> p.getCharacterName().equals(character.getCharacterName()))
            .findFirst()
            .orElseThrow(() -> new RuntimeException("Friend not found in search result."))
            .addFriend(notificationValidator);
    }

    private void searchForPossibleFriends(String characterName) {
        WebElement friendNameInputField = communityPage.getFriendNameInputField();
        friendNameInputField.clear();
        friendNameInputField.sendKeys(characterName);
    }

    private void verifyCannotSendFriendRequest(SeleniumCharacter character) {
        openAddFriendPage();

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
}
