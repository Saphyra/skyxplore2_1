package selenium.aanew.test.community.friendship;

import lombok.Builder;
import selenium.aanew.logic.domain.SeleniumAccount;
import selenium.aanew.logic.domain.SeleniumCharacter;
import selenium.aanew.logic.domain.SeleniumFriendRequest;
import selenium.aanew.logic.page.CommunityPage;
import selenium.aanew.test.community.friendship.helper.FriendshipTestHelper;
import selenium.aanew.test.community.util.CommunityTestHelper;
import selenium.aanew.test.community.util.CommunityTestInitializer;

import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@Builder
public class AcceptFriendRequestTest {
    private final CommunityTestInitializer communityTestInitializer;
    private final CommunityTestHelper communityTestHelper;
    private final FriendshipTestHelper friendshipTestHelper;
    private final CommunityPage communityPage;

    public void testAcceptFriendRequest() {
        List<SeleniumAccount> accounts = communityTestInitializer.registerAccounts(new int[]{1, 1});

        SeleniumAccount account = accounts.get(0);
        SeleniumCharacter character = account.getCharacter(0);
        communityTestHelper.goToCommunityPageOf(account, character);

        SeleniumAccount otherAccount = accounts.get(1);
        SeleniumCharacter otherCharacter = otherAccount.getCharacter(0);
        friendshipTestHelper.sendFriendRequestTo(otherCharacter);

        communityTestHelper.goToCommunityPageOf(otherAccount, otherCharacter, 1);

        acceptFriendRequest(character);

        verifyFriendRequestDisappeared(character);

        communityPage.getFriendsPageButton().click();
        verifyFriendInList(character);

        communityTestHelper.goToCommunityPageOf(account, character);
        verifyFriendInList(otherCharacter);

        verifySentFriendRequestDisappeared(otherCharacter);
    }

    private void verifyFriendRequestDisappeared(SeleniumCharacter character) {
        communityPage.getFriendsPageButton().click();
        assertFalse(
            communityPage.getFriendRequests().stream()
                .anyMatch(seleniumFriendRequest -> seleniumFriendRequest.getCharacterName().equals(character.getCharacterName()))
        );
    }

    private void acceptFriendRequest(SeleniumCharacter character) {
        communityPage.getFriendRequestsPageButton().click();
        List<SeleniumFriendRequest> friendRequests = communityPage.getFriendRequests();
        friendRequests.stream()
            .filter(seleniumFriendRequest -> seleniumFriendRequest.getCharacterName().equals(character.getCharacterName()))
            .findFirst()
            .orElseThrow(() -> new RuntimeException("FriendRequest not found."))
            .accept();
    }

    private void verifySentFriendRequestDisappeared(SeleniumCharacter otherCharacter) {
        communityPage.getSentFriendRequestsPageButton().click();
        assertFalse(
            communityPage.getSentFriendRequests().stream()
                .anyMatch(sentFriendRequest -> sentFriendRequest.getCharacterName().equals(otherCharacter.getCharacterName()))
        );
    }

    private void verifyFriendInList(SeleniumCharacter character) {
        assertTrue(
            communityPage.getFriends().stream()
                .anyMatch(friend -> friend.getCharacterName().equals(character.getCharacterName()))
        );
    }
}
