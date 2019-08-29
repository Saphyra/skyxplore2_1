package com.github.saphyra.selenium.test.community.mail.send;

import com.github.saphyra.selenium.logic.domain.SeleniumAccount;
import com.github.saphyra.selenium.logic.domain.SeleniumCharacter;
import com.github.saphyra.selenium.logic.domain.localization.Page;
import com.github.saphyra.selenium.logic.page.CommunityPage;
import com.github.saphyra.selenium.test.community.helper.CommunityTestHelper;
import com.github.saphyra.selenium.test.community.helper.CommunityTestInitializer;
import com.github.saphyra.selenium.test.community.helper.SendMailHelper;
import lombok.Builder;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

import static com.github.saphyra.selenium.logic.util.LocalizationUtil.getAdditionalContent;

@Builder
public class SendMailChangedAddresseeTest {
    private static final String MESSAGE_CODE_EMPTY_ADDRESSEE = "empty-addressee";

    private final WebDriver driver;
    private final CommunityTestInitializer communityTestInitializer;
    private final CommunityTestHelper communityTestHelper;
    private final CommunityPage communityPage;
    private final SendMailHelper sendMailHelper;

    public void testSendMailChangedAddressee() {
        List<SeleniumAccount> accounts = communityTestInitializer.registerAccounts(new int[]{1, 1});

        SeleniumAccount account = accounts.get(0);
        communityTestHelper.goToCommunityPageOf(account, account.getCharacter(0));

        communityPage.getWriteNewMailButton().click();

        sendMailHelper.setSubject()
            .setAddressee(accounts.get(1).getCharacter(0))
            .setMessage();


        WebElement addresseeField = communityPage.getAddresseeInputField();
        addresseeField.clear();
        addresseeField.sendKeys(SeleniumCharacter.createRandomCharacterName());

        sendMailHelper.verifyCannotSendMail(getAdditionalContent(driver, Page.COMMUNITY, MESSAGE_CODE_EMPTY_ADDRESSEE));
    }
}
