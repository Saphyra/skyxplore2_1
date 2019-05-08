package com.github.saphyra.selenium.test.community.friendship;

import com.github.saphyra.selenium.test.community.helper.CommunityTestHelper;
import com.github.saphyra.selenium.test.community.helper.CommunityTestInitializer;
import com.github.saphyra.selenium.test.community.helper.FriendshipTestHelper;
import lombok.Builder;
import com.github.saphyra.selenium.logic.domain.SeleniumAccount;
import com.github.saphyra.selenium.logic.domain.SeleniumCharacter;

import java.util.List;

import static com.github.saphyra.selenium.logic.domain.SeleniumCharacter.CHARACTER_NAME_PREFIX;

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
