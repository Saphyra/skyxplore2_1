package com.github.saphyra.selenium.test.community.block;

import static org.junit.Assert.assertTrue;

import java.util.List;

import lombok.Builder;
import com.github.saphyra.selenium.logic.domain.SeleniumAccount;
import com.github.saphyra.selenium.logic.domain.SeleniumCharacter;
import com.github.saphyra.selenium.logic.page.CommunityPage;
import com.github.saphyra.selenium.test.community.helper.BlockTestHelper;
import com.github.saphyra.selenium.test.community.helper.CommunityTestHelper;
import com.github.saphyra.selenium.test.community.helper.CommunityTestInitializer;
import com.github.saphyra.selenium.test.community.helper.FriendshipTestHelper;

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

        communityPage.getFriendsMainPageButton().click();
        communityPage.getSentFriendRequestsPageButton().click();
        assertTrue(communityPage.getSentFriendRequests().isEmpty());

        communityTestHelper.goToCommunityPageOf(otherAccount, otherCharacter);
        communityPage.getSentFriendRequestsPageButton().click();
        assertTrue(communityPage.getFriendRequests().isEmpty());
    }
}
