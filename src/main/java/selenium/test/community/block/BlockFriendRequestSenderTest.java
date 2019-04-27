package selenium.test.community.block;

import lombok.Builder;
import selenium.logic.domain.SeleniumAccount;
import selenium.logic.domain.SeleniumCharacter;
import selenium.logic.page.CommunityPage;
import selenium.test.community.helper.BlockTestHelper;
import selenium.test.community.helper.CommunityTestHelper;
import selenium.test.community.helper.CommunityTestInitializer;
import selenium.test.community.helper.FriendshipTestHelper;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Builder
public class BlockFriendRequestSenderTest {
    private final CommunityTestInitializer communityTestInitializer;
    private final CommunityTestHelper communityTestHelper;
    private final FriendshipTestHelper friendshipTestHelper;
    private final CommunityPage communityPage;
    private final BlockTestHelper blockTestHelper;

    public void testBlockFriendRequestSender() {
        List<SeleniumAccount> accounts = communityTestInitializer.registerAccounts(new int[]{1, 1});

        SeleniumAccount account = accounts.get(0);
        SeleniumCharacter character = account.getCharacter(0);
        communityTestHelper.goToCommunityPageOf(account, character);

        SeleniumAccount otherAccount = accounts.get(1);
        SeleniumCharacter otherCharacter = otherAccount.getCharacter(0);
        friendshipTestHelper.sendFriendRequestTo(otherCharacter);

        communityTestHelper.goToCommunityPageOf(otherAccount, otherCharacter, 1);

        friendshipTestHelper.blockFriendRequestSender(character);
        assertThat(communityPage.getFriendRequests()).isEmpty();

        communityPage.getBlockCharactersPageButton().click();
        assertThat(blockTestHelper.getBlockedCharacters().stream()
            .anyMatch(blockedCharacter -> blockedCharacter.getCharacterName().equals(character.getCharacterName())))
            .isTrue();
    }
}
