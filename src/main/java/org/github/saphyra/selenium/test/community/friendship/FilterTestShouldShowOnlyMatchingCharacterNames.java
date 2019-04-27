package org.github.saphyra.selenium.test.community.friendship;

import lombok.Builder;
import org.github.saphyra.selenium.logic.domain.SeleniumAccount;
import org.github.saphyra.selenium.test.community.helper.FriendshipTestHelper;
import org.github.saphyra.selenium.test.community.helper.CommunityTestHelper;
import org.github.saphyra.selenium.test.community.helper.CommunityTestInitializer;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Builder
public class FilterTestShouldShowOnlyMatchingCharacterNames {
    private final CommunityTestInitializer communityTestInitializer;
    private final CommunityTestHelper communityTestHelper;
    private final FriendshipTestHelper friendshipTestHelper;

    public void testFilterShouldShowOnlyMatchingCharacterNames() {
        List<SeleniumAccount> accounts = communityTestInitializer.registerAccounts(new int[]{1, 2});

        SeleniumAccount account = accounts.get(0);
        communityTestHelper.goToCommunityPageOf(account, account.getCharacter(0));

        SeleniumAccount otherAccount = accounts.get(1);

        friendshipTestHelper.searchForPossibleFriends(otherAccount.getCharacter(0));
        friendshipTestHelper.verifySearchResult(
            Stream.concat(account.getCharacters().stream(), Stream.of(otherAccount.getCharacter(1))).collect(Collectors.toList()),
            Arrays.asList(accounts.get(1).getCharacter(0))
        );
    }
}