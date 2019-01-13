package selenium.test.community.mail;

import lombok.Builder;
import selenium.logic.domain.SeleniumAccount;
import selenium.logic.page.CommunityPage;
import selenium.test.community.mail.helper.SendMailHelper;
import selenium.test.community.util.CommunityTestHelper;
import selenium.test.community.util.CommunityTestInitializer;

import java.util.List;

@Builder
public class SendMailEmptyAddresseeTest {
    private static final String NOTIFICATION_ADDRESSEE_IS_EMPTY = "A címzett megadása kötelező!";

    private final CommunityTestInitializer communityTestInitializer;
    private final CommunityTestHelper communityTestHelper;
    private final CommunityPage communityPage;
    private final SendMailHelper sendMailHelper;

    public void testSendMailEmptyAddressee() {
        List<SeleniumAccount> accounts = communityTestInitializer.registerAccounts(new int[]{1});

        SeleniumAccount account = accounts.get(0);
        communityTestHelper.goToCommunityPageOf(account, account.getCharacter(0));

        communityPage.getWriteNewMailButton().click();

        sendMailHelper.setSubject()
            .setMessage()
            .verifyCannotSendMail(NOTIFICATION_ADDRESSEE_IS_EMPTY);
    }
}
