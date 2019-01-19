package selenium.test.community.friendship;

import lombok.Builder;
import selenium.logic.domain.SeleniumAccount;
import selenium.logic.domain.SeleniumCharacter;
import selenium.logic.page.CommunityPage;
import selenium.test.community.helper.FriendshipTestHelper;
import selenium.test.community.helper.CommunityTestHelper;
import selenium.test.community.helper.CommunityTestInitializer;

import java.util.List;

import static junit.framework.TestCase.assertEquals;

@Builder
public class SendFriendRequestTest {
    private final CommunityTestInitializer communityTestInitializer;
    private final CommunityTestHelper communityTestHelper;
    private final FriendshipTestHelper friendshipTestHelper;
    private final CommunityPage communityPage;

    public void testSendFriendRequest() {
        List<SeleniumAccount> accounts = communityTestInitializer.registerAccounts(new int[]{1, 1});

        SeleniumAccount account = accounts.get(0);
        communityTestHelper.goToCommunityPageOf(account, account.getCharacter(0));

        SeleniumCharacter otherCharacter = accounts.get(1).getCharacter(0);
        friendshipTestHelper.sendFriendRequestTo(otherCharacter);

        friendshipTestHelper.verifyCannotSendFriendRequestTo(otherCharacter);

        communityPage.closeAddFriendPage();

        friendshipTestHelper.verifySentFriendRequestInList(otherCharacter);

        verifyFriendRequestArrived(accounts);
    }

    private void verifyFriendRequestArrived(List<SeleniumAccount> accounts) {
        SeleniumAccount account = accounts.get(1);
        communityTestHelper.goToCommunityPageOf(account, account.getCharacter(0), 1);
        assertEquals(1, communityPage.getNumberOfFriendRequests());

        SeleniumCharacter character = accounts.get(0).getCharacter(0);
        friendshipTestHelper.verifyCannotSendFriendRequestTo(character);

        communityPage.closeAddFriendPage();

        friendshipTestHelper.isFriendRequestArrived(character);
    }
}
