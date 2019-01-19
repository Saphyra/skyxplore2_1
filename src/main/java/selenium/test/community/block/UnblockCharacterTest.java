package selenium.test.community.block;

import lombok.Builder;
import selenium.logic.domain.SeleniumAccount;
import selenium.logic.domain.SeleniumCharacter;
import selenium.logic.validator.NotificationValidator;
import selenium.test.community.helper.BlockTestHelper;
import selenium.test.community.helper.CommunityTestHelper;
import selenium.test.community.helper.CommunityTestInitializer;
import selenium.test.community.helper.FriendshipTestHelper;
import selenium.test.community.helper.SendMailHelper;

import java.util.List;

@Builder
public class UnblockCharacterTest {
    private final CommunityTestInitializer communityTestInitializer;
    private final CommunityTestHelper communityTestHelper;
    private final BlockTestHelper blockTestHelper;
    private final FriendshipTestHelper friendshipTestHelper;
    private final NotificationValidator notificationValidator;
    private final SendMailHelper sendMailHelper;

    public void testUnblockCharacter() {
        List<SeleniumAccount> accounts = communityTestInitializer.registerAccounts(new int[]{1, 1});

        SeleniumAccount account = accounts.get(0);
        SeleniumCharacter character = account.getCharacter(0);

        SeleniumAccount otherAccount = accounts.get(1);
        SeleniumCharacter otherCharacter = otherAccount.getCharacter(0);

        communityTestHelper.goToCommunityPageOf(account, character);

        blockTestHelper.blockCharacter(otherCharacter);
        blockTestHelper.getBlockedCharacters().stream()
            .findFirst()
            .orElseThrow(() -> new RuntimeException("BlockedCharacter not found"))
            .unblock(notificationValidator);

        friendshipTestHelper.sendFriendRequestTo(otherCharacter);
        sendMailHelper.sendMailTo(otherCharacter);
    }
}
