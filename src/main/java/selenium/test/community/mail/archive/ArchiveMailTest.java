package selenium.test.community.mail.archive;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Optional;

import lombok.Builder;
import selenium.logic.domain.Mail;
import selenium.logic.domain.SeleniumAccount;
import selenium.logic.domain.SeleniumCharacter;
import selenium.logic.page.CommunityPage;
import selenium.logic.validator.NotificationValidator;
import selenium.test.community.helper.CommunityTestHelper;
import selenium.test.community.helper.CommunityTestInitializer;
import selenium.test.community.helper.MailTestHelper;
import selenium.test.community.helper.SendMailHelper;

@Builder
public class ArchiveMailTest {
    private final CommunityTestInitializer communityTestInitializer;
    private final CommunityTestHelper communityTestHelper;
    private final CommunityPage communityPage;
    private final SendMailHelper sendMailHelper;
    private final MailTestHelper mailTestHelper;
    private final NotificationValidator notificationValidator;

    public void testArchiveMail() {
        List<SeleniumAccount> accounts = communityTestInitializer.registerAccounts(new int[]{1, 1});

        SeleniumAccount account = accounts.get(0);
        SeleniumCharacter character = account.getCharacter(0);
        communityTestHelper.goToCommunityPageOf(account, character);

        SeleniumAccount otherAccount = accounts.get(1);
        SeleniumCharacter otherCharacter = otherAccount.getCharacter(0);
        sendMailHelper.sendMailTo(otherCharacter);

        communityTestHelper.goToCommunityPageOf(otherAccount, otherCharacter, 1);

        Mail mail = findMail(character)
            .orElseThrow(() -> new RuntimeException("Mail not found"));

        mail.archive(notificationValidator);

        mailTestHelper.verifyIncomingNoIncomingMails();
        assertEquals(0, mailTestHelper.getNumberOfUnreadMails());

        assertTrue(
            mailTestHelper.getArchivedMails().stream()
                .anyMatch(m -> m.getSender().equals(character.getCharacterName()))
        );
    }

    private Optional<Mail> findMail(SeleniumCharacter character) {
        return mailTestHelper.getReceivedMails().stream()
            .filter(m -> m.getSender().equals(character.getCharacterName()))
            .findFirst();
    }
}
