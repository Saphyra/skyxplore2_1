package selenium.test.community.mail.archive;

import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import selenium.logic.domain.Mail;
import selenium.logic.domain.MessageCodes;
import selenium.logic.domain.SeleniumAccount;
import selenium.logic.domain.SeleniumCharacter;
import selenium.logic.page.CommunityPage;
import selenium.logic.validator.NotificationValidator;
import selenium.test.community.helper.CommunityTestHelper;
import selenium.test.community.helper.CommunityTestInitializer;
import selenium.test.community.helper.MailTestHelper;
import selenium.test.community.helper.SendMailHelper;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Builder
@Slf4j
public class BulkArchiveMailTest {
    private static final String OTHER_SUBJECT = "other_subject";
    private static final String MESSAGE_CODE_MAILS_ARCHIVED = "MAILS_ARCHIVED";

    private final CommunityTestInitializer communityTestInitializer;
    private final CommunityTestHelper communityTestHelper;
    private final CommunityPage communityPage;
    private final SendMailHelper sendMailHelper;
    private final MailTestHelper mailTestHelper;
    private final NotificationValidator notificationValidator;
    private final MessageCodes messageCodes;

    public void testBulkArchiveMail() {
        List<SeleniumAccount> accounts = communityTestInitializer.registerAccounts(new int[]{1, 1});

        SeleniumAccount account = accounts.get(0);
        SeleniumCharacter character = account.getCharacter(0);
        communityTestHelper.goToCommunityPageOf(account, character);

        SeleniumAccount otherAccount = accounts.get(1);
        SeleniumCharacter otherCharacter = otherAccount.getCharacter(0);
        sendMailHelper.sendMailTo(otherCharacter);

        sendMailHelper.openWriteMailPage();

        sendMailHelper.setSubject(OTHER_SUBJECT)
            .setAddressee(otherCharacter)
            .setMessage()
            .sendMail();

        communityTestHelper.goToCommunityPageOf(otherAccount, otherCharacter, 2);

        communityPage.getSelectAllIncomingMailsButton().click();

        mailTestHelper.selectBulkArchiveOption();

        communityPage.getExecuteBulkEditButtonForReceivedMails().click();
        notificationValidator.verifyNotificationVisibility(messageCodes.get(MESSAGE_CODE_MAILS_ARCHIVED));

        mailTestHelper.verifyIncomingNoIncomingMails();

        List<Mail> archivedMails = mailTestHelper.getArchivedMails();
        assertThat(archivedMails).hasSize(2);

        verifyContainsSubject(archivedMails, SendMailHelper.DEFAULT_SUBJECT);
        verifyContainsSubject(archivedMails, OTHER_SUBJECT);
    }

    private void verifyContainsSubject(List<Mail> archivedMails, String otherSubject) {
        boolean result = archivedMails.stream()
            .anyMatch(mail -> mail.getSubject().equals(otherSubject));
        assertThat(result).isTrue();
    }
}
