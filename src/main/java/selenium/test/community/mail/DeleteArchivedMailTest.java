package selenium.test.community.mail;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import lombok.Builder;
import selenium.logic.domain.SeleniumAccount;
import selenium.logic.domain.SeleniumCharacter;
import selenium.logic.page.CommunityPage;
import selenium.logic.validator.NotificationValidator;
import selenium.test.community.helper.MailTestHelper;
import selenium.test.community.helper.SendMailHelper;
import selenium.test.community.helper.CommunityTestHelper;
import selenium.test.community.helper.CommunityTestInitializer;

@Builder
public class DeleteArchivedMailTest {
    private final CommunityTestInitializer communityTestInitializer;
    private final CommunityTestHelper communityTestHelper;
    private final CommunityPage communityPage;
    private final SendMailHelper sendMailHelper;
    private final MailTestHelper mailTestHelper;
    private final NotificationValidator notificationValidator;

    public void testDeleteArchivedMail() {
        List<SeleniumAccount> accounts = communityTestInitializer.registerAccounts(new int[]{1, 1});

        SeleniumAccount account = accounts.get(0);
        SeleniumCharacter character = account.getCharacter(0);
        communityTestHelper.goToCommunityPageOf(account, character);

        SeleniumAccount otherAccount = accounts.get(1);
        SeleniumCharacter otherCharacter = otherAccount.getCharacter(0);
        sendMailHelper.sendMailTo(otherCharacter);

        communityTestHelper.goToCommunityPageOf(otherAccount, otherCharacter, 1);

        mailTestHelper.getReceivedMails().stream()
            .findAny()
            .orElseThrow(() -> new RuntimeException("Mail not found"))
            .archive(notificationValidator);

        mailTestHelper.getArchivedMails().stream()
            .findAny()
            .orElseThrow(() -> new RuntimeException("Mail not found"))
            .delete(notificationValidator);

        assertTrue(mailTestHelper.getArchivedMails().isEmpty());

        communityTestHelper.goToCommunityPageOf(account, character);
        assertEquals(1, mailTestHelper.getSentMails().size());
    }
}
