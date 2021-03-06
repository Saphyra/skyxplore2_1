package com.github.saphyra.selenium.test.community.mail.mark;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;

import lombok.Builder;
import com.github.saphyra.selenium.logic.domain.Mail;
import com.github.saphyra.selenium.logic.domain.SeleniumAccount;
import com.github.saphyra.selenium.logic.domain.SeleniumCharacter;
import com.github.saphyra.selenium.logic.page.CommunityPage;
import com.github.saphyra.selenium.test.community.helper.MailTestHelper;
import com.github.saphyra.selenium.test.community.helper.SendMailHelper;
import com.github.saphyra.selenium.test.community.helper.CommunityTestHelper;
import com.github.saphyra.selenium.test.community.helper.CommunityTestInitializer;

@Builder
public class ReadMailTest {
    private final CommunityTestInitializer communityTestInitializer;
    private final CommunityTestHelper communityTestHelper;
    private final CommunityPage communityPage;
    private final SendMailHelper sendMailHelper;
    private final MailTestHelper mailTestHelper;

    public void testReadMail() {
        List<SeleniumAccount> accounts = communityTestInitializer.registerAccounts(new int[]{1, 1});

        SeleniumAccount account = accounts.get(0);
        SeleniumCharacter character = account.getCharacter(0);
        communityTestHelper.goToCommunityPageOf(account, character);

        SeleniumAccount otherAccount = accounts.get(1);
        SeleniumCharacter otherCharacter = otherAccount.getCharacter(0);
        sendMailHelper.sendMailTo(otherCharacter);

        communityTestHelper.goToCommunityPageOf(otherAccount, otherCharacter, 1);

        assertEquals(1, mailTestHelper.getNumberOfUnreadMails());

        Mail receivedMail = getMail(character);
        assertFalse(receivedMail.isRead());
        receivedMail.read();

        communityTestHelper.goToCommunityPageOf(otherAccount, otherCharacter, 0);
        assertEquals(0, mailTestHelper.getNumberOfUnreadMails());

        assertTrue(getMail(character).isRead());
    }

    private Mail getMail(SeleniumCharacter from) {
        return mailTestHelper.getIncomingMails().stream()
            .filter(mail -> mail.getSender().equals(from.getCharacterName()))
            .findFirst()
            .orElseThrow(() -> new RuntimeException("Received mail not found."));
    }
}
