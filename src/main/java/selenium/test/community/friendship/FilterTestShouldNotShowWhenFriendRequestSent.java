package selenium.test.community.friendship;

import lombok.Builder;
import selenium.logic.domain.SeleniumAccount;
import selenium.logic.domain.SeleniumCharacter;
import selenium.test.community.helper.CommunityTestHelper;
import selenium.test.community.helper.CommunityTestInitializer;
import selenium.test.community.helper.FriendshipTestHelper;

import java.util.Arrays;
import java.util.List;

import static selenium.logic.domain.SeleniumCharacter.CHARACTER_NAME_PREFIX;

@Builder
public class FilterTestShouldNotShowWhenFriendRequestSent {
    private final CommunityTestInitializer communityTestInitializer;
    private final CommunityTestHelper communityTestHelper;
    private final FriendshipTestHelper friendshipTestHelper;

    public void testFilterShouldNotShowWhenFriendRequestSent() {
        List<SeleniumAccount> accounts = communityTestInitializer.registerAccounts(new int[]{2, 2});
        SeleniumCharacter searchCharacter = new SeleniumCharacter(CHARACTER_NAME_PREFIX);

        SeleniumAccount account = accounts.get(0);
        SeleniumCharacter character = account.getCharacter(0);
        communityTestHelper.goToCommunityPageOf(account, character);

        SeleniumAccount otherAccount = accounts.get(1);
        SeleniumCharacter otherCharacter = otherAccount.getCharacter(0);

        friendshipTestHelper.sendFriendRequestTo(otherCharacter);

        friendshipTestHelper.searchForPossibleFriends(searchCharacter);
        friendshipTestHelper.verifySearchResult(
            Arrays.asList(otherAccount.getCharacter(0)),
            Arrays.asList(otherAccount.getCharacter(1))
        );

        communityTestHelper.goToCommunityPageOf(otherAccount, otherCharacter, 1);

        friendshipTestHelper.searchForPossibleFriends(searchCharacter);
        friendshipTestHelper.verifySearchResult(
            Arrays.asList(account.getCharacter(0)),
            Arrays.asList(account.getCharacter(1))
        );
    }
}
