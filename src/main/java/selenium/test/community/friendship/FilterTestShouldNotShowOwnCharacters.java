package selenium.test.community.friendship;

import lombok.Builder;
import selenium.logic.domain.SeleniumAccount;
import selenium.logic.domain.SeleniumCharacter;
import selenium.test.community.helper.FriendshipTestHelper;
import selenium.test.community.helper.CommunityTestHelper;
import selenium.test.community.helper.CommunityTestInitializer;

import java.util.List;

import static selenium.logic.domain.SeleniumCharacter.CHARACTER_NAME_PREFIX;

@Builder
public class FilterTestShouldNotShowOwnCharacters {
    private final CommunityTestInitializer communityTestInitializer;
    private final CommunityTestHelper communityTestHelper;
    private final FriendshipTestHelper friendshipTestHelper;

    public void testFilterShouldNotShowOwnCharacters() {
        List<SeleniumAccount> accounts = communityTestInitializer.registerAccounts(new int[]{2, 1});

        SeleniumAccount account = accounts.get(0);
        communityTestHelper.goToCommunityPageOf(account, account.getCharacter(0));

        friendshipTestHelper.searchForPossibleFriends(new SeleniumCharacter(CHARACTER_NAME_PREFIX));
        friendshipTestHelper.verifySearchResult(account.getCharacters(), accounts.get(1).getCharacters());
    }
}
