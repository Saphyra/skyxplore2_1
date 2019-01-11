package selenium.aanew.test.community.friendship;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import lombok.Builder;
import selenium.aanew.logic.domain.SeleniumAccount;
import selenium.aanew.test.community.friendship.helper.FilterTestHelper;
import selenium.aanew.test.community.util.CommunityTestHelper;
import selenium.aanew.test.community.util.CommunityTestInitializer;

@Builder
public class FilterTestShouldShowOnlyMatchingCharacterNames {
    private final CommunityTestInitializer communityTestInitializer;
    private final CommunityTestHelper communityTestHelper;
    private final FilterTestHelper filterTestHelper;

    public void testFilterShouldShowOnlyMatchingCharacterNames() {
        List<SeleniumAccount> accounts = communityTestInitializer.registerAccounts(new int[]{1, 2});

        SeleniumAccount account = accounts.get(0);
        communityTestHelper.goToCommunityPageOf(account, account.getCharacter(0));

        SeleniumAccount otherAccount = accounts.get(1);

        filterTestHelper.searchForPossibleFriends(otherAccount.getCharacter(0).getCharacterName());
        filterTestHelper.verifySearchResult(
            Stream.concat(account.getCharacters().stream(), Stream.of(otherAccount.getCharacter(1))).collect(Collectors.toList()),
            Arrays.asList(accounts.get(1).getCharacter(0))
        );
    }
}
