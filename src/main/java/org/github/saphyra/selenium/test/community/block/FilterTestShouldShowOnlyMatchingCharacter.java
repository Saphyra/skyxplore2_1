package org.github.saphyra.selenium.test.community.block;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

import java.util.List;

import lombok.Builder;
import org.github.saphyra.selenium.logic.domain.BlockableCharacter;
import org.github.saphyra.selenium.logic.domain.SeleniumAccount;
import org.github.saphyra.selenium.logic.domain.SeleniumCharacter;
import org.github.saphyra.selenium.test.community.helper.BlockTestHelper;
import org.github.saphyra.selenium.test.community.helper.CommunityTestHelper;
import org.github.saphyra.selenium.test.community.helper.CommunityTestInitializer;

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

        List<BlockableCharacter> blockableCharacters = blockTestHelper.searchCharacterCanBeBlocked(otherCharacter.getCharacterName());
        assertEquals(1, blockableCharacters.size());
        assertThat(blockableCharacters.stream()
            .anyMatch(blockableCharacter -> blockableCharacter.getCharacterName().equals(otherCharacter.getCharacterName())))
            .isTrue();
    }
}