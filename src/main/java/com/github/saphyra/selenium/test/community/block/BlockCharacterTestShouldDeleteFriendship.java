package com.github.saphyra.selenium.test.community.block;

import lombok.Builder;
import com.github.saphyra.selenium.logic.domain.SeleniumAccount;
import com.github.saphyra.selenium.logic.domain.SeleniumCharacter;
import com.github.saphyra.selenium.logic.page.CommunityPage;
import com.github.saphyra.selenium.test.community.helper.BlockTestHelper;
import com.github.saphyra.selenium.test.community.helper.CommunityTestHelper;
import com.github.saphyra.selenium.test.community.helper.CommunityTestInitializer;
import com.github.saphyra.selenium.test.community.helper.FriendshipTestHelper;

import java.util.List;

import static org.junit.Assert.assertTrue;

@Builder
public class BlockCharacterTestShouldDeleteFriendship {
    private final CommunityTestInitializer communityTestInitializer;
    private final CommunityTestHelper communityTestHelper;
    private final BlockTestHelper blockTestHelper;
    private final FriendshipTestHelper friendshipTestHelper;
    private final CommunityPage communityPage;

    public void testBlockCharacterShouldDeleteFriendship() {
        List<SeleniumAccount> accounts = communityTestInitializer.registerAccounts(new int[]{1, 1});

        SeleniumAccount account = accounts.get(0);
        SeleniumCharacter character = account.getCharacter(0);

        SeleniumAccount otherAccount = accounts.get(1);
        SeleniumCharacter otherCharacter = otherAccount.getCharacter(0);

        communityTestHelper.goToCommunityPageOf(account, character);
        friendshipTestHelper.sendFriendRequestTo(otherCharacter);

        communityTestHelper.goToCommunityPageOf(otherAccount, otherCharacter, 1);
        friendshipTestHelper.acceptFriendRequest(character);

        communityTestHelper.goToCommunityPageOf(account, character);
        blockTestHelper.blockCharacter(otherCharacter);

        communityPage.getFriendsMainPageButton().click();
        communityPage.getOpenFriendsPageButton().click();
        assertTrue(communityPage.getFriends().isEmpty());

        communityTestHelper.goToCommunityPageOf(otherAccount, otherCharacter);
        assertTrue(communityPage.getFriends().isEmpty());
    }
}
