package selenium.aanew.test.community.friendship;

import lombok.Builder;
import selenium.aanew.logic.domain.SeleniumAccount;
import selenium.aanew.test.community.friendship.helper.FriendshipTestHelper;
import selenium.aanew.test.community.util.CommunityTestHelper;
import selenium.aanew.test.community.util.CommunityTestInitializer;

import java.util.List;

import static selenium.aanew.logic.domain.SeleniumCharacter.CHARACTER_NAME_PREFIX;

@Builder
public class FilterTestShouldNotShowOwnCharacters {
    private final CommunityTestInitializer communityTestInitializer;
    private final CommunityTestHelper communityTestHelper;
    private final FriendshipTestHelper friendshipTestHelper;

    public void testFilterShouldNotShowOwnCharacters() {
        List<SeleniumAccount> accounts = communityTestInitializer.registerAccounts(new int[]{2, 1});

        SeleniumAccount account = accounts.get(0);
        communityTestHelper.goToCommunityPageOf(account, account.getCharacter(0));

        friendshipTestHelper.searchForPossibleFriends(CHARACTER_NAME_PREFIX);
        friendshipTestHelper.verifySearchResult(account.getCharacters(), accounts.get(1).getCharacters());
    }
}
