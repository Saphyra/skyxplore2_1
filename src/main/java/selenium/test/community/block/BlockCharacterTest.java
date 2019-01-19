package selenium.test.community.block;

import lombok.Builder;
import org.openqa.selenium.WebDriver;
import selenium.logic.domain.SeleniumAccount;
import selenium.logic.domain.SeleniumCharacter;
import selenium.logic.page.CommunityPage;
import selenium.test.community.helper.BlockTestHelper;
import selenium.test.community.helper.CommunityTestHelper;
import selenium.test.community.helper.CommunityTestInitializer;
import selenium.test.community.helper.FriendshipTestHelper;
import selenium.test.community.helper.SendMailHelper;

import java.util.List;

import static selenium.logic.util.Util.cleanNotifications;

@Builder
public class BlockCharacterTest {
    private final WebDriver driver;
    private final CommunityTestInitializer communityTestInitializer;
    private final CommunityTestHelper communityTestHelper;
    private final SendMailHelper sendMailHelper;
    private final FriendshipTestHelper friendshipTestHelper;
    private final BlockTestHelper blockTestHelper;
    private final CommunityPage communityPage;

    public void testBlockCharacter() {
        List<SeleniumAccount> accounts = communityTestInitializer.registerAccounts(new int[]{1, 1, 1, 1});

        SeleniumAccount account = accounts.get(0);
        SeleniumCharacter character = account.getCharacter(0);
        communityTestHelper.goToCommunityPageOf(account, character);

        SeleniumAccount account2 = accounts.get(1);
        SeleniumCharacter character2 = account2.getCharacter(0);
        sendMailHelper.sendMailTo(character2);

        SeleniumAccount account3 = accounts.get(2);
        SeleniumCharacter character3 = account3.getCharacter(0);
        friendshipTestHelper.sendFriendRequestTo(character3);

        SeleniumAccount account4 = accounts.get(3);
        SeleniumCharacter character4 = account4.getCharacter(0);
        friendshipTestHelper.sendFriendRequestTo(character4);

        communityTestHelper.goToCommunityPageOf(account4, character4, 1);
        friendshipTestHelper.acceptFriendRequest(character);

        communityTestHelper.goToCommunityPageOf(account, character);

        blockTestHelper.blockCharacter(character2);
        cleanNotifications(driver);
        blockTestHelper.blockCharacter(character3);
        cleanNotifications(driver);
        blockTestHelper.blockCharacter(character4);

        verifyBlocker(character2, character3, character4);
    }

    private void verifyBlocker(SeleniumCharacter character2, SeleniumCharacter character3, SeleniumCharacter character4) {
        sendMailHelper.verifyAddresseeNotFound(character2)
            .verifyAddresseeNotFound(character3)
            .verifyAddresseeNotFound(character4);

        communityPage.closeWriteMailPage();

        friendshipTestHelper.verifyCannotSendFriendRequestTo(character2);
        friendshipTestHelper.verifyCannotSendFriendRequestTo(character3);
        friendshipTestHelper.verifyCannotSendFriendRequestTo(character4);
    }
}
