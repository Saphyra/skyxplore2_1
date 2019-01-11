package selenium.aanew.test.community.friendship.helper;

import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.stream.Collectors;

import org.openqa.selenium.WebElement;

import lombok.RequiredArgsConstructor;
import selenium.aanew.logic.domain.PossibleFriend;
import selenium.aanew.logic.domain.SeleniumCharacter;
import selenium.aanew.logic.page.CommunityPage;

@RequiredArgsConstructor
public class FilterTestHelper {
    private final CommunityPage communityPage;

    public void searchForPossibleFriends(String characterName) {
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

    public void verifySearchResult(List<SeleniumCharacter> shouldNotContain, List<SeleniumCharacter> shouldContain) {
        List<PossibleFriend> searchResult = communityPage.getCharactersCanBeFriendList();
        List<String> characterNames = searchResult.stream()
            .map(PossibleFriend::getCharacterName)
            .collect(Collectors.toList());

        shouldNotContain.forEach(character -> assertTrue(characterNames.stream().noneMatch(s -> s.equals(character.getCharacterName()))));
        shouldContain.forEach(character -> assertTrue(characterNames.stream().anyMatch(s -> s.equals(character.getCharacterName()))));
    }
}
