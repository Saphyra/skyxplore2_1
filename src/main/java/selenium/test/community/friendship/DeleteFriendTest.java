package selenium.test.community.friendship;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import lombok.Builder;
import selenium.logic.domain.MessageCodes;
import selenium.logic.domain.SeleniumAccount;
import selenium.logic.domain.SeleniumCharacter;
import selenium.logic.page.CommunityPage;
import selenium.logic.validator.NotificationValidator;
import selenium.test.community.helper.CommunityTestHelper;
import selenium.test.community.helper.CommunityTestInitializer;
import selenium.test.community.helper.FriendshipTestHelper;

@Builder
public class DeleteFriendTest {
    private static final String MESSAGE_CODE_FRIEND_DELETED = "FRIEND_DELETED";

    private final CommunityTestInitializer communityTestInitializer;
    private final CommunityTestHelper communityTestHelper;
    private final FriendshipTestHelper friendshipTestHelper;
    private final CommunityPage communityPage;
    private final NotificationValidator notificationValidator;
    private final MessageCodes messageCodes;

    public void testDeleteFriend() {
        List<SeleniumAccount> accounts = communityTestInitializer.registerAccounts(new int[]{1, 1});

        SeleniumAccount account = accounts.get(0);
        SeleniumCharacter character = account.getCharacter(0);
        communityTestHelper.goToCommunityPageOf(account, character);

        SeleniumAccount otherAccount = accounts.get(1);
        SeleniumCharacter otherCharacter = otherAccount.getCharacter(0);
        friendshipTestHelper.sendFriendRequestTo(otherCharacter);

        communityTestHelper.goToCommunityPageOf(otherAccount, otherCharacter, 1);

        friendshipTestHelper.acceptFriendRequest(character);

        communityPage.getOpenFriendsPageButton().click();
        communityPage.getFriends().stream()
            .findAny()
            .orElseThrow(() -> new RuntimeException("Friendship not found"))
            .delete();

        notificationValidator.verifyOnlyOneNotification(messageCodes.get(MESSAGE_CODE_FRIEND_DELETED));
        assertThat(communityPage.getFriends()).isEmpty();

        communityTestHelper.goToCommunityPageOf(account, character, 0);
        assertThat(communityPage.getFriends()).isEmpty();
    }
}
