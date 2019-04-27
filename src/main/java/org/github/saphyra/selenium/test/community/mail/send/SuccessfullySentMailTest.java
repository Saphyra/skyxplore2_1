package org.github.saphyra.selenium.test.community.mail.send;

import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import org.github.saphyra.selenium.logic.domain.Mail;
import org.github.saphyra.selenium.logic.domain.MessageCodes;
import org.github.saphyra.selenium.logic.domain.SeleniumAccount;
import org.github.saphyra.selenium.logic.domain.SeleniumCharacter;
import org.github.saphyra.selenium.logic.page.CommunityPage;
import org.github.saphyra.selenium.test.community.helper.MailTestHelper;
import org.github.saphyra.selenium.test.community.helper.SendMailHelper;
import org.github.saphyra.selenium.test.community.helper.CommunityTestHelper;
import org.github.saphyra.selenium.test.community.helper.CommunityTestInitializer;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@Builder
@Slf4j
public class SuccessfullySentMailTest {
    public static final String MESSAGE_CODE_MAIL_SENT = "MAIL_SENT";

    private final CommunityTestInitializer communityTestInitializer;
    private final CommunityTestHelper communityTestHelper;
    private final CommunityPage communityPage;
    private final SendMailHelper sendMailHelper;
    private final MailTestHelper mailTestHelper;
    private final MessageCodes messageCodes;

    public void testSuccessfullySentMail() {
        List<SeleniumAccount> accounts = communityTestInitializer.registerAccounts(new int[]{1, 1});

        SeleniumAccount account = accounts.get(0);
        SeleniumCharacter character = account.getCharacter(0);

        SeleniumAccount otherAccount = accounts.get(1);
        SeleniumCharacter otherCharacter = otherAccount.getCharacter(0);

        communityTestHelper.goToCommunityPageOf(account, character);

        communityPage.getWriteNewMailButton().click();

        sendMailHelper.setSubject()
            .setAddressee(otherCharacter)
            .setMessage()
            .sendMail()
            .verifyMailSent(messageCodes.get(MESSAGE_CODE_MAIL_SENT));

        verifyMailAtSentMails(otherCharacter);
        verifyMailArrived(otherAccount, character);
    }

    private void verifyMailAtSentMails(SeleniumCharacter addressee) {
        List<Mail> sentMails = mailTestHelper.getSentMails();
        log.info("{}", sentMails);
        assertEquals(1, sentMails.size());
        assertTrue(
            sentMails.stream().anyMatch(mail -> mail.getAddressee().equals(addressee.getCharacterName()))
        );
    }

    private void verifyMailArrived(SeleniumAccount otherAccount, SeleniumCharacter character) {
        communityTestHelper.goToCommunityPageOf(otherAccount, otherAccount.getCharacter(0), 1);

        List<Mail> receivedMails = mailTestHelper.getIncomingMails();
        assertTrue(
            receivedMails.stream().anyMatch(mail -> mail.getSender().equals(character.getCharacterName()))
        );
    }
}
