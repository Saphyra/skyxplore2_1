package com.github.saphyra.selenium.test.community.mail.archive;

import lombok.Builder;
import com.github.saphyra.selenium.logic.domain.Mail;
import com.github.saphyra.selenium.logic.domain.SeleniumAccount;
import com.github.saphyra.selenium.logic.domain.SeleniumCharacter;
import com.github.saphyra.selenium.logic.page.CommunityPage;
import com.github.saphyra.selenium.logic.validator.NotificationValidator;
import com.github.saphyra.selenium.test.community.helper.CommunityTestHelper;
import com.github.saphyra.selenium.test.community.helper.CommunityTestInitializer;
import com.github.saphyra.selenium.test.community.helper.MailTestHelper;
import com.github.saphyra.selenium.test.community.helper.SendMailHelper;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

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

        mailTestHelper.verifyNoIncomingMails();
        assertEquals(0, mailTestHelper.getNumberOfUnreadMails());

        assertTrue(
            mailTestHelper.getArchivedMails().stream()
                .anyMatch(m -> m.getSender().equals(character.getCharacterName()))
        );
    }

    private Optional<Mail> findMail(SeleniumCharacter character) {
        return mailTestHelper.getIncomingMails().stream()
            .filter(m -> m.getSender().equals(character.getCharacterName()))
            .findFirst();
    }
}
