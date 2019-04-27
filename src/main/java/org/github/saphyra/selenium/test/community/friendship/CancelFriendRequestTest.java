package org.github.saphyra.selenium.test.community.friendship;

import static junit.framework.TestCase.assertEquals;

import java.util.List;

import lombok.Builder;
import org.github.saphyra.selenium.logic.domain.SeleniumAccount;
import org.github.saphyra.selenium.logic.domain.SeleniumCharacter;
import org.github.saphyra.selenium.logic.page.CommunityPage;
import org.github.saphyra.selenium.test.community.helper.CommunityTestHelper;
import org.github.saphyra.selenium.test.community.helper.CommunityTestInitializer;
import org.github.saphyra.selenium.test.community.helper.FriendshipTestHelper;

@Builder
public class CancelFriendRequestTest {
    private final CommunityTestInitializer communityTestInitializer;
    private final CommunityTestHelper communityTestHelper;
    private final FriendshipTestHelper friendshipTestHelper;
    private final CommunityPage communityPage;

    public void testCancelFriendRequest() {
        List<SeleniumAccount> accounts = communityTestInitializer.registerAccounts(new int[]{1, 1});

        SeleniumAccount account = accounts.get(0);
        communityTestHelper.goToCommunityPageOf(account, account.getCharacter(0));

        SeleniumCharacter character = accounts.get(1).getCharacter(0);
        friendshipTestHelper.sendFriendRequestTo(character);

        communityPage.getSentFriendRequestsPageButton().click();

        communityPage.getSentFriendRequests().stream()
            .filter(sentFriendRequest -> sentFriendRequest.getCharacterName().equals(character.getCharacterName()))
            .findFirst()
            .orElseThrow(() -> new RuntimeException("SentFriendRequest not found."))
            .cancel();

        communityPage.getOpenFriendsPageButton().click();
        friendshipTestHelper.verifyFriendRequestCanBeSentTo(character);

        communityTestHelper.goToCommunityPageOf(accounts.get(1), character, 0);

        assertEquals(0, communityPage.getNumberOfFriendRequests());
        friendshipTestHelper.verifyFriendRequestCanBeSentTo(account.getCharacter(0));
    }
}
