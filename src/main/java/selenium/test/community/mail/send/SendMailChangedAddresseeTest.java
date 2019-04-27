package selenium.test.community.mail.send;

import lombok.Builder;
import org.openqa.selenium.WebElement;
import selenium.logic.domain.MessageCodes;
import selenium.logic.domain.SeleniumAccount;
import selenium.logic.domain.SeleniumCharacter;
import selenium.logic.page.CommunityPage;
import selenium.test.community.helper.SendMailHelper;
import selenium.test.community.helper.CommunityTestHelper;
import selenium.test.community.helper.CommunityTestInitializer;

import java.util.List;

@Builder
public class SendMailChangedAddresseeTest {
    private static final String MESSAGE_CODE_NO_ADDRESSEE = "ADDRESSEE_MUST_BE_SET";

    private final CommunityTestInitializer communityTestInitializer;
    private final CommunityTestHelper communityTestHelper;
    private final CommunityPage communityPage;
    private final SendMailHelper sendMailHelper;
    private final MessageCodes messageCodes;

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

        sendMailHelper.verifyCannotSendMail(messageCodes.get(MESSAGE_CODE_NO_ADDRESSEE));
    }
}