package selenium.test.community.block;

import lombok.Builder;
import selenium.logic.domain.SeleniumAccount;
import selenium.logic.domain.SeleniumCharacter;
import selenium.test.community.helper.BlockTestHelper;
import selenium.test.community.helper.CommunityTestHelper;
import selenium.test.community.helper.CommunityTestInitializer;
import selenium.test.community.helper.SendMailHelper;

import java.util.List;

@Builder
public class BlockCharacterTestShouldNotSendMail {
    private final CommunityTestInitializer communityTestInitializer;
    private final CommunityTestHelper communityTestHelper;
    private final BlockTestHelper blockTestHelper;
    private final SendMailHelper sendMailHelper;

    public void testBlockCharacterShouldNotSendMail() {
        List<SeleniumAccount> accounts = communityTestInitializer.registerAccounts(new int[]{1, 1});

        SeleniumAccount account = accounts.get(0);
        SeleniumCharacter character = account.getCharacter(0);
        communityTestHelper.goToCommunityPageOf(account, character);

        SeleniumAccount otherAccount = accounts.get(1);
        SeleniumCharacter otherCharacter = otherAccount.getCharacter(0);

        blockTestHelper.blockCharacter(otherCharacter);

        sendMailHelper.verifyAddresseeNotFound(otherCharacter);

        communityTestHelper.goToCommunityPageOf(otherAccount, otherCharacter);
        sendMailHelper.verifyAddresseeNotFound(character);
    }
}
