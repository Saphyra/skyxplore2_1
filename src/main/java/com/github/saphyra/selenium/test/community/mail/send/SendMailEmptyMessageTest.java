package com.github.saphyra.selenium.test.community.mail.send;

import com.github.saphyra.selenium.logic.domain.SeleniumAccount;
import com.github.saphyra.selenium.logic.domain.localization.Page;
import com.github.saphyra.selenium.logic.page.CommunityPage;
import com.github.saphyra.selenium.test.community.helper.CommunityTestHelper;
import com.github.saphyra.selenium.test.community.helper.CommunityTestInitializer;
import com.github.saphyra.selenium.test.community.helper.SendMailHelper;
import lombok.Builder;
import org.openqa.selenium.WebDriver;

import java.util.List;

import static com.github.saphyra.selenium.logic.util.LocalizationUtil.getAdditionalContent;

@Builder
public class SendMailEmptyMessageTest {
    private static final String MESSAGE_CODE_EMPTY_MESSAGE = "MESSAGE_MUST_NOT_BE_EMPTY";

    private final WebDriver driver;
    private final CommunityTestInitializer communityTestInitializer;
    private final CommunityTestHelper communityTestHelper;
    private final CommunityPage communityPage;
    private final SendMailHelper sendMailHelper;

    public void testSendMailEmptyMessage() {
        List<SeleniumAccount> accounts = communityTestInitializer.registerAccounts(new int[]{1, 1});

        SeleniumAccount account = accounts.get(0);
        communityTestHelper.goToCommunityPageOf(account, account.getCharacter(0));

        communityPage.getWriteNewMailButton().click();

        sendMailHelper.setAddressee(accounts.get(1).getCharacter(0))
            .setSubject()
            .verifyCannotSendMail(getAdditionalContent(driver, Page.COMMUNITY, MESSAGE_CODE_EMPTY_MESSAGE));
    }
}
