package selenium.test.community.block;

import lombok.Builder;
import selenium.logic.domain.SeleniumAccount;
import selenium.logic.domain.SeleniumCharacter;
import selenium.test.community.helper.BlockTestHelper;
import selenium.test.community.helper.CommunityTestHelper;
import selenium.test.community.helper.CommunityTestInitializer;

import java.util.List;

import static org.junit.Assert.assertTrue;

@Builder
public class FilterTestShouldNotShowAlreadyBlocked {
    private final CommunityTestInitializer communityTestInitializer;
    private final CommunityTestHelper communityTestHelper;
    private final BlockTestHelper blockTestHelper;

    public void testFilterShouldNotShowAlreadyBlocked() {
        List<SeleniumAccount> accounts = communityTestInitializer.registerAccounts(new int[]{1, 1});

        SeleniumAccount account = accounts.get(0);
        SeleniumCharacter character = account.getCharacter(0);
        communityTestHelper.goToCommunityPageOf(account, character);

        SeleniumCharacter otherCharacter = accounts.get(1).getCharacter(0);
        blockTestHelper.blockCharacter(otherCharacter);

        assertTrue(blockTestHelper.searchForBlockableCharacters(otherCharacter.getCharacterName()).isEmpty());
    }
}
