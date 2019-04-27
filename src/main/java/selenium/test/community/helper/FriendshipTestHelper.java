package selenium.test.community.helper;

import lombok.RequiredArgsConstructor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import selenium.logic.domain.PossibleFriend;
import selenium.logic.domain.SeleniumCharacter;
import selenium.logic.domain.SeleniumFriendRequest;
import selenium.logic.page.CommunityPage;
import selenium.logic.validator.NotificationValidator;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertTrue;

@RequiredArgsConstructor
public class FriendshipTestHelper {
    private final WebDriver driver;
    private final CommunityPage communityPage;
    private final NotificationValidator notificationValidator;

    public void sendFriendRequestTo(SeleniumCharacter character) {
        searchForPossibleFriends(character);

        communityPage.getAddFriendSearchResult().stream()
            .filter(p -> p.getCharacterName().equals(character.getCharacterName()))
            .findFirst()
            .orElseThrow(() -> new RuntimeException("Friend not found in search result."))
            .addFriend(notificationValidator);
    }

    public void acceptFriendRequest(SeleniumCharacter character) {
        communityPage.getFriendRequestsPageButton().click();
        communityPage.getFriendRequests().stream()
            .filter(seleniumFriendRequest -> seleniumFriendRequest.getCharacterName().equals(character.getCharacterName()))
            .findFirst()
            .orElseThrow(() -> new RuntimeException("FriendRequest not found."))
            .accept();
    }

    public void blockFriendRequestSender(SeleniumCharacter character) {
        communityPage.getFriendRequestsPageButton().click();
        communityPage.getFriendRequests().stream()
            .filter(seleniumFriendRequest -> seleniumFriendRequest.getCharacterName().equals(character.getCharacterName()))
            .findFirst()
            .orElseThrow(() -> new RuntimeException("FriendRequest not found."))
            .blockSender();
    }

    public void searchForPossibleFriends(SeleniumCharacter character) {
        if (!communityPage.getAddFriendContainer().isDisplayed()) {
            openAddFriendPage();
        }

        WebElement friendNameInputField = communityPage.getFriendNameInputField();
        friendNameInputField.clear();
        friendNameInputField.sendKeys(character.getCharacterName());
    }

    public void verifyCannotSendFriendRequestTo(SeleniumCharacter character) {
        searchForPossibleFriends(character);
        verifySearchResult(
            Arrays.asList(character),
            Collections.emptyList()
        );
    }

    public void verifyFriendRequestCanBeSentTo(SeleniumCharacter character) {
        searchForPossibleFriends(character);
        verifySearchResult(
            Collections.emptyList(),
            Arrays.asList(character)
        );
    }

    public void verifySentFriendRequestInList(SeleniumCharacter character) {
        communityPage.getSentFriendRequestsPageButton().click();
        assertTrue(
            communityPage.getSentFriendRequests().stream()
                .anyMatch(sentFriendRequest -> sentFriendRequest.getCharacterName().equals(character.getCharacterName()))
        );
    }

    public void isFriendRequestArrived(SeleniumCharacter character) {
        communityPage.getFriendRequestsPageButton().click();
        List<SeleniumFriendRequest> friendRequests = communityPage.getFriendRequests();
        assertTrue(
            friendRequests.stream()
                .anyMatch(seleniumFriendRequest -> seleniumFriendRequest.getCharacterName().equals(character.getCharacterName()))
        );
    }

    public void verifySearchResult(List<SeleniumCharacter> shouldNotContain, List<SeleniumCharacter> shouldContain) {
        List<String> characterNames = communityPage.getAddFriendSearchResult().stream()
            .map(PossibleFriend::getCharacterName)
            .collect(Collectors.toList());

        shouldNotContain.forEach(character -> assertTrue(characterNames.stream().noneMatch(s -> s.equals(character.getCharacterName()))));
        shouldContain.forEach(character -> assertTrue(characterNames.stream().anyMatch(s -> s.equals(character.getCharacterName()))));
    }

    private void openAddFriendPage() {
        communityPage.getFriendsMainPageButton().click();
        communityPage.getOpenFriendsPageButton().click();
        communityPage.getAddFriendButton().click();
        new WebDriverWait(driver, 1).until(ExpectedConditions.visibilityOf(communityPage.getAddFriendContainer()));
    }
}
