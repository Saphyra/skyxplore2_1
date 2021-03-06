package com.github.saphyra.selenium.test.community.mail.send;

import lombok.Builder;
import com.github.saphyra.selenium.logic.domain.Mail;
import com.github.saphyra.selenium.logic.domain.SeleniumAccount;
import com.github.saphyra.selenium.logic.domain.SeleniumCharacter;
import com.github.saphyra.selenium.logic.page.CommunityPage;
import com.github.saphyra.selenium.test.community.helper.CommunityTestHelper;
import com.github.saphyra.selenium.test.community.helper.CommunityTestInitializer;
import com.github.saphyra.selenium.test.community.helper.MailTestHelper;
import com.github.saphyra.selenium.test.community.helper.SendMailHelper;

import java.util.List;

import static org.junit.Assert.assertEquals;

@Builder
public class ReplyMailTest {
    private static final String REPLY_PREFIX = "Re: ";

    private final CommunityTestInitializer communityTestInitializer;
    private final CommunityTestHelper communityTestHelper;
    private final CommunityPage communityPage;
    private final SendMailHelper sendMailHelper;
    private final MailTestHelper mailTestHelper;

    public void testReplyMail() {
        List<SeleniumAccount> accounts = communityTestInitializer.registerAccounts(new int[]{1, 1});

        SeleniumAccount account = accounts.get(0);
        SeleniumCharacter character = account.getCharacter(0);
        communityTestHelper.goToCommunityPageOf(account, character);

        SeleniumAccount otherAccount = accounts.get(1);
        SeleniumCharacter otherCharacter = otherAccount.getCharacter(0);
        sendMailHelper.sendMailTo(otherCharacter);

        communityTestHelper.goToCommunityPageOf(otherAccount, otherCharacter, 1);

        Mail mail = mailTestHelper.getMail();
        mail.read();
        mail.reply();

        sendMailHelper.setMessage().sendMail();

        communityTestHelper.goToCommunityPageOf(account, character, 1);

        Mail reply = mailTestHelper.getMail();
        assertEquals(REPLY_PREFIX + SendMailHelper.DEFAULT_SUBJECT, reply.getSubject());
    }
}
