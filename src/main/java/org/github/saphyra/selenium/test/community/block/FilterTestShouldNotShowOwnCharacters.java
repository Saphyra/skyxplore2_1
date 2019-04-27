package org.github.saphyra.selenium.test.community.block;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import lombok.Builder;
import org.github.saphyra.selenium.logic.domain.BlockableCharacter;
import org.github.saphyra.selenium.logic.domain.SeleniumAccount;
import org.github.saphyra.selenium.logic.domain.SeleniumCharacter;
import org.github.saphyra.selenium.test.community.helper.BlockTestHelper;
import org.github.saphyra.selenium.test.community.helper.CommunityTestHelper;
import org.github.saphyra.selenium.test.community.helper.CommunityTestInitializer;

@Builder
public class FilterTestShouldNotShowOwnCharacters {
    private final CommunityTestInitializer communityTestInitializer;
    private final CommunityTestHelper communityTestHelper;
    private final BlockTestHelper blockTestHelper;

    public void testFilterShouldNotShowOwnCharacters() {
        List<SeleniumAccount> accounts = communityTestInitializer.registerAccounts(new int[]{2, 1});

        SeleniumAccount account = accounts.get(0);
        SeleniumCharacter character = account.getCharacter(0);
        communityTestHelper.goToCommunityPageOf(account, character);

        List<BlockableCharacter> blockableCharacters = blockTestHelper.searchCharacterCanBeBlocked(SeleniumCharacter.CHARACTER_NAME_PREFIX);
        assertThat(blockableCharacters.stream()
            .noneMatch(blockableCharacter -> blockableCharacter.getCharacterName().equals(account.getCharacter(1).getCharacterName())))
            .isTrue();

        SeleniumAccount otherAccount = accounts.get(1);
        SeleniumCharacter otherCharacter = otherAccount.getCharacter(0);
        assertThat(
            blockableCharacters.stream()
                .anyMatch(blockableCharacter -> blockableCharacter.getCharacterName().equals(otherCharacter.getCharacterName()))
        ).isTrue();
    }
}
