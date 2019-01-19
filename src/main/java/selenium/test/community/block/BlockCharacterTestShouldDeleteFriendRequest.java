package selenium.test.community.block;

import lombok.Builder;
import selenium.logic.domain.SeleniumAccount;
import selenium.logic.domain.SeleniumCharacter;
import selenium.logic.page.CommunityPage;
import selenium.test.community.helper.BlockTestHelper;
import selenium.test.community.helper.CommunityTestHelper;
import selenium.test.community.helper.CommunityTestInitializer;
import selenium.test.community.helper.FriendshipTestHelper;

import java.util.List;

import static org.junit.Assert.assertTrue;

@Builder
public class BlockCharacterTestShouldDeleteFriendRequest {
    private final CommunityTestInitializer communityTestInitializer;
    private final CommunityTestHelper communityTestHelper;
    private final BlockTestHelper blockTestHelper;
    private final FriendshipTestHelper friendshipTestHelper;
    private final CommunityPage communityPage;

    public void testBlockCharacterShouldDeleteFriendRequest() {
        List<SeleniumAccount> accounts = communityTestInitializer.registerAccounts(new int[]{1, 1});

        SeleniumAccount account = accounts.get(0);
        SeleniumCharacter character = account.getCharacter(0);

        SeleniumAccount otherAccount = accounts.get(1);
        SeleniumCharacter otherCharacter = otherAccount.getCharacter(0);

        communityTestHelper.goToCommunityPageOf(account, character);
        friendshipTestHelper.sendFriendRequestTo(otherCharacter);

        blockTestHelper.blockCharacter(otherCharacter);

        assertTrue(communityPage.getSentFriendRequests().isEmpty());

        communityTestHelper.goToCommunityPageOf(otherAccount, otherCharacter);
        assertTrue(communityPage.getFriendRequests().isEmpty());
    }
}
