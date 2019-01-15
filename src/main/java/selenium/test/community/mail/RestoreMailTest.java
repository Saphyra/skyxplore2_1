package selenium.test.community.mail;

import lombok.Builder;
import selenium.logic.domain.SeleniumAccount;
import selenium.logic.domain.SeleniumCharacter;
import selenium.logic.page.CommunityPage;
import selenium.test.community.mail.helper.MailTestHelper;
import selenium.test.community.mail.helper.SendMailHelper;
import selenium.test.community.util.CommunityTestHelper;
import selenium.test.community.util.CommunityTestInitializer;

import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@Builder
public class RestoreMailTest {
    private final CommunityTestInitializer communityTestInitializer;
    private final CommunityTestHelper communityTestHelper;
    private final CommunityPage communityPage;
    private final SendMailHelper sendMailHelper;
    private final MailTestHelper mailTestHelper;

    public void testRestoreMail() {
        List<SeleniumAccount> accounts = communityTestInitializer.registerAccounts(new int[]{1, 1});

        SeleniumAccount account = accounts.get(0);
        SeleniumCharacter character = account.getCharacter(0);
        communityTestHelper.goToCommunityPageOf(account, character);

        SeleniumAccount otherAccount = accounts.get(1);
        SeleniumCharacter otherCharacter = otherAccount.getCharacter(0);
        sendMailHelper.sendMailTo(otherCharacter);

        communityTestHelper.goToCommunityPageOf(otherAccount, otherCharacter, 1);

        mailTestHelper.getReceivedMails().stream()
            .filter(m -> m.getSender().equals(character.getCharacterName()))
            .findFirst()
            .orElseThrow(() -> new RuntimeException("Mail not found"))
            .archive();

        mailTestHelper.getArchivedMails().stream()
            .filter(m -> m.getSender().equals(character.getCharacterName()))
            .findFirst()
            .orElseThrow(() -> new RuntimeException("Archived Mail not found"))
            .restore();

        assertFalse(
            mailTestHelper.getArchivedMails().stream()
                .anyMatch(m -> m.getSender().equals(character.getCharacterName()))
        );

        assertTrue(
            mailTestHelper.getReceivedMails().stream()
                .anyMatch(m -> m.getSender().equals(character.getCharacterName()))
        );
    }
}
