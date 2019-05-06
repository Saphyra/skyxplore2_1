package org.github.saphyra.selenium.test.community.friendship;

import lombok.Builder;
import org.github.saphyra.selenium.logic.domain.SeleniumAccount;
import org.github.saphyra.selenium.logic.domain.SeleniumCharacter;
import org.github.saphyra.selenium.logic.domain.SeleniumFriendRequest;
import org.github.saphyra.selenium.logic.page.CommunityPage;
import org.github.saphyra.selenium.test.community.helper.FriendshipTestHelper;
import org.github.saphyra.selenium.test.community.helper.CommunityTestHelper;
import org.github.saphyra.selenium.test.community.helper.CommunityTestInitializer;

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

        communityPage.getOpenFriendsPageButton().click();
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
