package selenium.aanew.test.community.friendship;

import static selenium.aanew.logic.domain.SeleniumCharacter.CHARACTER_NAME_PREFIX;

import java.util.List;

import lombok.Builder;
import selenium.aanew.logic.domain.SeleniumAccount;
import selenium.aanew.test.community.friendship.helper.FilterTestHelper;
import selenium.aanew.test.community.util.CommunityTestHelper;
import selenium.aanew.test.community.util.CommunityTestInitializer;

@Builder
public class FilterTestShouldNotShowOwnCharacters {
    private final CommunityTestInitializer communityTestInitializer;
    private final CommunityTestHelper communityTestHelper;
    private final FilterTestHelper filterTestHelper;

    public void testFilterShouldNotShowOwnCharacters() {
        List<SeleniumAccount> accounts = communityTestInitializer.registerAccounts(new int[]{2, 1});

        SeleniumAccount account = accounts.get(0);
        communityTestHelper.goToCommunityPageOf(account, account.getCharacter(0));

        filterTestHelper.searchForPossibleFriends(CHARACTER_NAME_PREFIX);
        filterTestHelper.verifySearchResult(account.getCharacters(), accounts.get(1).getCharacters());
    }
}
