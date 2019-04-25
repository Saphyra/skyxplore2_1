package selenium.test.community.friendship;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static selenium.logic.util.WaitUtil.sleep;

import java.util.List;

import lombok.Builder;
import selenium.logic.domain.SeleniumAccount;
import selenium.logic.domain.SeleniumCharacter;
import selenium.logic.page.CommunityPage;
import selenium.test.community.helper.CommunityTestHelper;
import selenium.test.community.helper.CommunityTestInitializer;
import selenium.test.community.helper.FriendshipTestHelper;

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

        friendshipTestHelper.acceptFriendRequest(character);

        verifyFriendRequestDisappeared(character);

        verifyFriendInList(character);

        communityTestHelper.goToCommunityPageOf(account, character);
        verifyFriendInList(otherCharacter);

        verifySentFriendRequestDisappeared(otherCharacter);
    }

    private void verifyFriendRequestDisappeared(SeleniumCharacter character) {
        communityPage.getOpenFriendsPageButton().click();
        assertFalse(
            communityPage.getFriendRequests().stream()
                .anyMatch(seleniumFriendRequest -> seleniumFriendRequest.getCharacterName().equals(character.getCharacterName()))
        );
    }


    private void verifySentFriendRequestDisappeared(SeleniumCharacter otherCharacter) {
        communityPage.getSentFriendRequestsPageButton().click();
        assertFalse(
            communityPage.getSentFriendRequests().stream()
                .anyMatch(sentFriendRequest -> sentFriendRequest.getCharacterName().equals(otherCharacter.getCharacterName()))
        );
    }

    private void verifyFriendInList(SeleniumCharacter character) {
        communityPage.getOpenFriendsPageButton().click();
        sleep(500);
        assertTrue(
            communityPage.getFriends().stream()
                .anyMatch(friend -> friend.getCharacterName().equals(character.getCharacterName()))
        );
    }
}
