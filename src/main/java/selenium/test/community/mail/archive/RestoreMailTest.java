package selenium.test.community.mail.archive;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import lombok.Builder;
import selenium.logic.domain.SeleniumAccount;
import selenium.logic.domain.SeleniumCharacter;
import selenium.logic.page.CommunityPage;
import selenium.logic.validator.NotificationValidator;
import selenium.test.community.helper.CommunityTestHelper;
import selenium.test.community.helper.CommunityTestInitializer;
import selenium.test.community.helper.MailTestHelper;
import selenium.test.community.helper.SendMailHelper;

@Builder
public class RestoreMailTest {
    private final CommunityTestInitializer communityTestInitializer;
    private final CommunityTestHelper communityTestHelper;
    private final CommunityPage communityPage;
    private final SendMailHelper sendMailHelper;
    private final MailTestHelper mailTestHelper;
    private final NotificationValidator notificationValidator;

    public void testRestoreMail() {
        List<SeleniumAccount> accounts = communityTestInitializer.registerAccounts(new int[]{1, 1});

        SeleniumAccount account = accounts.get(0);
        SeleniumCharacter character = account.getCharacter(0);
        communityTestHelper.goToCommunityPageOf(account, character);

        SeleniumAccount otherAccount = accounts.get(1);
        SeleniumCharacter otherCharacter = otherAccount.getCharacter(0);
        sendMailHelper.sendMailTo(otherCharacter);

        communityTestHelper.goToCommunityPageOf(otherAccount, otherCharacter, 1);

        mailTestHelper.getIncomingMails().stream()
            .filter(m -> m.getSender().equals(character.getCharacterName()))
            .findFirst()
            .orElseThrow(() -> new RuntimeException("Mail not found"))
            .archive(notificationValidator);

        mailTestHelper.getArchivedMails().stream()
            .filter(m -> m.getSender().equals(character.getCharacterName()))
            .findFirst()
            .orElseThrow(() -> new RuntimeException("Archived Mail not found"))
            .restore(notificationValidator);

        mailTestHelper.verifyNoArchivedMails();

        assertThat(
            mailTestHelper.getIncomingMails().stream()
                .anyMatch(m -> m.getSender().equals(character.getCharacterName()))
        ).isTrue();
    }
}
