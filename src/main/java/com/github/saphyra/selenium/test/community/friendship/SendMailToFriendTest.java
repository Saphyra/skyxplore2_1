package com.github.saphyra.selenium.test.community.friendship;

import com.github.saphyra.selenium.test.community.helper.CommunityTestHelper;
import com.github.saphyra.selenium.test.community.helper.CommunityTestInitializer;
import com.github.saphyra.selenium.test.community.helper.FriendshipTestHelper;
import com.github.saphyra.selenium.test.community.helper.MailTestHelper;
import com.github.saphyra.selenium.test.community.helper.SendMailHelper;
import lombok.Builder;
import com.github.saphyra.selenium.logic.domain.SeleniumAccount;
import com.github.saphyra.selenium.logic.domain.SeleniumCharacter;
import com.github.saphyra.selenium.logic.page.CommunityPage;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Builder
public class SendMailToFriendTest {
    private final CommunityTestInitializer communityTestInitializer;
    private final CommunityTestHelper communityTestHelper;
    private final FriendshipTestHelper friendshipTestHelper;
    private final CommunityPage communityPage;
    private final SendMailHelper sendMailHelper;
    private final MailTestHelper mailTestHelper;

    public void testSendMailToFriend() {
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
            .sendMail();

        assertThat(communityPage.getSendMailContainer().isDisplayed()).isTrue();
        assertThat(communityPage.getAddresseeInputField().getAttribute("value")).isEqualTo(character.getCharacterName());

        sendMailHelper.setMessage()
            .setSubject()
            .sendMail();

        communityTestHelper.goToCommunityPageOf(account, character, 1);
        assertThat(mailTestHelper.getIncomingMails().stream()
            .anyMatch(mail -> mail.getSender().equals(otherCharacter.getCharacterName()))).isTrue();
    }
}
