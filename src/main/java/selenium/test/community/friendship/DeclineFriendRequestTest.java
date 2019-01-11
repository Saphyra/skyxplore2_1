package selenium.test.community.friendship;

import lombok.Builder;
import selenium.logic.domain.SeleniumAccount;
import selenium.logic.domain.SeleniumCharacter;
import selenium.logic.domain.SeleniumFriendRequest;
import selenium.logic.page.CommunityPage;
import selenium.test.community.friendship.helper.FriendshipTestHelper;
import selenium.test.community.util.CommunityTestHelper;
import selenium.test.community.util.CommunityTestInitializer;

import java.util.List;

import static org.junit.Assert.assertFalse;

@Builder
public class DeclineFriendRequestTest {
    private final CommunityTestInitializer communityTestInitializer;
    private final CommunityTestHelper communityTestHelper;
    private final FriendshipTestHelper friendshipTestHelper;
    private final CommunityPage communityPage;

    public void testDeclineFriendRequest() {
        List<SeleniumAccount> accounts = communityTestInitializer.registerAccounts(new int[]{1, 1});

        SeleniumAccount account = accounts.get(0);
        communityTestHelper.goToCommunityPageOf(account, account.getCharacter(0));

        SeleniumCharacter otherCharacter = accounts.get(1).getCharacter(0);
        friendshipTestHelper.sendFriendRequestTo(otherCharacter);

        declineRequest(accounts, account.getCharacter(0));

        verifyFriendRequestDeclined(otherCharacter);

        communityPage.getFriendsPageButton().click();
        friendshipTestHelper.verifyFriendRequestCanBeSentTo(account.getCharacter(0));

        communityTestHelper.goToCommunityPageOf(account, account.getCharacter(0));
        friendshipTestHelper.verifyFriendRequestCanBeSentTo(otherCharacter);

        communityPage.closeAddFriendPage();

        verifySentFriendRequestDisappeared(otherCharacter);
    }

    private void declineRequest(List<SeleniumAccount> accounts, SeleniumCharacter character) {
        SeleniumAccount otherAccount = accounts.get(1);
        communityTestHelper.goToCommunityPageOf(otherAccount, otherAccount.getCharacter(0), 1);

        communityPage.getFriendRequestsPageButton().click();
        List<SeleniumFriendRequest> friendRequests = communityPage.getFriendRequests();
        friendRequests.stream()
            .filter(seleniumFriendRequest -> seleniumFriendRequest.getCharacterName().equals(character.getCharacterName()))
            .findFirst()
            .orElseThrow(() -> new RuntimeException("FriendRequest not found."))
            .decline();
    }

    private void verifyFriendRequestDeclined(SeleniumCharacter otherCharacter) {
        assertFalse(
            communityPage.getFriendRequests().stream()
                .anyMatch(seleniumFriendRequest -> seleniumFriendRequest.getCharacterName().equals(otherCharacter.getCharacterName()))
        );
    }

    private void verifySentFriendRequestDisappeared(SeleniumCharacter otherCharacter) {
        communityPage.getSentFriendRequestsPageButton().click();
        assertFalse(
            communityPage.getSentFriendRequests().stream()
                .anyMatch(sentFriendRequest -> sentFriendRequest.getCharacterName().equals(otherCharacter.getCharacterName()))
        );
    }
}
