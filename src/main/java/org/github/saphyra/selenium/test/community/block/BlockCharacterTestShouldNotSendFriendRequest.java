package org.github.saphyra.selenium.test.community.block;

import lombok.Builder;
import org.github.saphyra.selenium.logic.domain.SeleniumAccount;
import org.github.saphyra.selenium.logic.domain.SeleniumCharacter;
import org.github.saphyra.selenium.test.community.helper.BlockTestHelper;
import org.github.saphyra.selenium.test.community.helper.CommunityTestHelper;
import org.github.saphyra.selenium.test.community.helper.CommunityTestInitializer;
import org.github.saphyra.selenium.test.community.helper.FriendshipTestHelper;

import java.util.List;

@Builder
public class BlockCharacterTestShouldNotSendFriendRequest {
    private final CommunityTestInitializer communityTestInitializer;
    private final CommunityTestHelper communityTestHelper;
    private final BlockTestHelper blockTestHelper;
    private final FriendshipTestHelper friendshipTestHelper;

    public void testBlockCharacterShouldNotSendFriendRequest() {
        List<SeleniumAccount> accounts = communityTestInitializer.registerAccounts(new int[]{1, 1});

        SeleniumAccount account = accounts.get(0);
        SeleniumCharacter character = account.getCharacter(0);
        communityTestHelper.goToCommunityPageOf(account, character);

        SeleniumAccount otherAccount = accounts.get(1);
        SeleniumCharacter otherCharacter = otherAccount.getCharacter(0);

        blockTestHelper.blockCharacter(otherCharacter);
        friendshipTestHelper.verifyCannotSendFriendRequestTo(otherCharacter);

        communityTestHelper.goToCommunityPageOf(otherAccount, otherCharacter);
        friendshipTestHelper.verifyCannotSendFriendRequestTo(character);
    }
}
