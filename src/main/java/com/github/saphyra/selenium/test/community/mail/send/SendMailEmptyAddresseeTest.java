package com.github.saphyra.selenium.test.community.mail.send;

import lombok.Builder;
import com.github.saphyra.selenium.logic.domain.localization.MessageCodes;
import com.github.saphyra.selenium.logic.domain.SeleniumAccount;
import com.github.saphyra.selenium.logic.page.CommunityPage;
import com.github.saphyra.selenium.test.community.helper.SendMailHelper;
import com.github.saphyra.selenium.test.community.helper.CommunityTestHelper;
import com.github.saphyra.selenium.test.community.helper.CommunityTestInitializer;

import java.util.List;

@Builder
public class SendMailEmptyAddresseeTest {
    private static final String MESSAGE_CODE_NO_ADDRESSEE = "ADDRESSEE_MUST_BE_SET";

    private final CommunityTestInitializer communityTestInitializer;
    private final CommunityTestHelper communityTestHelper;
    private final CommunityPage communityPage;
    private final SendMailHelper sendMailHelper;
    private final MessageCodes messageCodes;

    public void testSendMailEmptyAddressee() {
        List<SeleniumAccount> accounts = communityTestInitializer.registerAccounts(new int[]{1});

        SeleniumAccount account = accounts.get(0);
        communityTestHelper.goToCommunityPageOf(account, account.getCharacter(0));

        communityPage.getWriteNewMailButton().click();

        sendMailHelper.setSubject()
            .setMessage()
            .verifyCannotSendMail(messageCodes.get(MESSAGE_CODE_NO_ADDRESSEE));
    }
}
