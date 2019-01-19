package selenium.test.community.block;

import lombok.Builder;
import selenium.logic.domain.SeleniumAccount;
import selenium.logic.domain.SeleniumCharacter;
import selenium.logic.page.CommunityPage;
import selenium.logic.validator.NotificationValidator;
import selenium.test.community.helper.BlockTestHelper;
import selenium.test.community.helper.CommunityTestHelper;
import selenium.test.community.helper.CommunityTestInitializer;
import selenium.test.community.helper.MailTestHelper;
import selenium.test.community.helper.SendMailHelper;

import java.util.List;

import static org.junit.Assert.assertTrue;

@Builder
public class BlockCharacterShouldUnableReplyMail {
    private static final String NOTIFICATION_CHARACTER_BLOCKED = "Üzenet küldése sikertelen.";

    private final CommunityTestInitializer communityTestInitializer;
    private final CommunityTestHelper communityTestHelper;
    private final BlockTestHelper blockTestHelper;
    private final SendMailHelper sendMailHelper;
    private final MailTestHelper mailTestHelper;
    private final NotificationValidator notificationValidator;
    private final CommunityPage communityPage;

    public void testBlockCharacterShouldUnableToReplyMail() {
        List<SeleniumAccount> accounts = communityTestInitializer.registerAccounts(new int[]{1, 1});

        SeleniumAccount account = accounts.get(0);
        SeleniumCharacter character = account.getCharacter(0);

        SeleniumAccount otherAccount = accounts.get(1);
        SeleniumCharacter otherCharacter = otherAccount.getCharacter(0);

        communityTestHelper.goToCommunityPageOf(account, character);
        sendMailHelper.sendMailTo(otherCharacter);
        blockTestHelper.blockCharacter(otherCharacter);

        communityTestHelper.goToCommunityPageOf(otherAccount, otherCharacter, 1);
        mailTestHelper.getMail().reply();
        sendMailHelper.setMessage()
            .sendMail();

        notificationValidator.verifyNotificationVisibility(NOTIFICATION_CHARACTER_BLOCKED);
        assertTrue(communityPage.getSendMailContainer().isDisplayed());
    }
}
