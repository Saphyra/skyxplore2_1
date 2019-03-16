package selenium.test.community.mail.send;

import lombok.Builder;
import selenium.logic.domain.MessageCodes;
import selenium.logic.domain.SeleniumAccount;
import selenium.logic.page.CommunityPage;
import selenium.test.community.helper.SendMailHelper;
import selenium.test.community.helper.CommunityTestHelper;
import selenium.test.community.helper.CommunityTestInitializer;

import java.util.List;

@Builder
public class SendMailEmptyMessageTest {
    private static final String MESSAGE_CODE_EMPTY_MESSAGE = "MESSAGE_MUST_NOT_BE_EMPTY";

    private final CommunityTestInitializer communityTestInitializer;
    private final CommunityTestHelper communityTestHelper;
    private final CommunityPage communityPage;
    private final SendMailHelper sendMailHelper;
    private final MessageCodes messageCodes;

    public void testSendMailEmptyMessage() {
        List<SeleniumAccount> accounts = communityTestInitializer.registerAccounts(new int[]{1, 1});

        SeleniumAccount account = accounts.get(0);
        communityTestHelper.goToCommunityPageOf(account, account.getCharacter(0));

        communityPage.getWriteNewMailButton().click();

        sendMailHelper.setAddressee(accounts.get(1).getCharacter(0))
            .setSubject()
            .verifyCannotSendMail(messageCodes.get(MESSAGE_CODE_EMPTY_MESSAGE));
    }
}
