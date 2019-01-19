package selenium.test.community.block;

import lombok.Builder;
import selenium.logic.domain.BlockableCharacter;
import selenium.logic.domain.SeleniumAccount;
import selenium.logic.domain.SeleniumCharacter;
import selenium.test.community.helper.BlockTestHelper;
import selenium.test.community.helper.CommunityTestHelper;
import selenium.test.community.helper.CommunityTestInitializer;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@Builder
public class FilterTestShouldShowOnlyMatchingCharacter {
    private final CommunityTestInitializer communityTestInitializer;
    private final CommunityTestHelper communityTestHelper;
    private final BlockTestHelper blockTestHelper;

    public void testFilterShouldShowOnlyMatchingCharacters() {
        List<SeleniumAccount> accounts = communityTestInitializer.registerAccounts(new int[]{1, 2});

        SeleniumAccount account = accounts.get(0);
        SeleniumCharacter character = account.getCharacter(0);
        communityTestHelper.goToCommunityPageOf(account, character);

        SeleniumAccount otherAccount = accounts.get(1);
        SeleniumCharacter otherCharacter = otherAccount.getCharacter(0);

        List<BlockableCharacter> blockableCharacters = blockTestHelper.searchForBlockableCharacters(otherCharacter.getCharacterName());
        assertEquals(1, blockableCharacters.size());
        assertTrue(
            blockableCharacters.stream()
                .anyMatch(blockableCharacter -> blockableCharacter.getCharacterName().equals(otherCharacter.getCharacterName()))
        );
    }
}
