package selenium.test.community.friendship;

import lombok.Builder;
import selenium.logic.domain.SeleniumAccount;
import selenium.logic.domain.SeleniumCharacter;
import selenium.test.community.helper.CommunityTestHelper;
import selenium.test.community.helper.CommunityTestInitializer;
import selenium.test.community.helper.FriendshipTestHelper;

import java.util.Collections;
import java.util.List;

@Builder
public class FilterTestShouldNotShowWhenAlreadyFriend {
    private final CommunityTestInitializer communityTestInitializer;
    private final CommunityTestHelper communityTestHelper;
    private final FriendshipTestHelper friendshipTestHelper;

    public void testFilterShouldNotShowFriends() {
        List<SeleniumAccount> accounts = communityTestInitializer.registerAccounts(new int[]{1, 1});

        SeleniumAccount account = accounts.get(0);
        SeleniumCharacter character = account.getCharacter(0);
        communityTestHelper.goToCommunityPageOf(account, character);

        SeleniumAccount otherAccount = accounts.get(1);
        SeleniumCharacter otherCharacter = otherAccount.getCharacter(0);

        friendshipTestHelper.sendFriendRequestTo(otherCharacter);
        communityTestHelper.goToCommunityPageOf(otherAccount, otherCharacter, 1);
        friendshipTestHelper.acceptFriendRequest(character);


        friendshipTestHelper.searchForPossibleFriends(character);
        friendshipTestHelper.verifySearchResult(account.getCharacters(), Collections.emptyList());

        communityTestHelper.goToCommunityPageOf(account, character);
        friendshipTestHelper.searchForPossibleFriends(character);
        friendshipTestHelper.verifySearchResult(otherAccount.getCharacters(), Collections.emptyList());
    }
}
