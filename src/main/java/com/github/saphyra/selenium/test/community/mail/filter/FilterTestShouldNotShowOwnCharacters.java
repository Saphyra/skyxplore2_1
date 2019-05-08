package com.github.saphyra.selenium.test.community.mail.filter;

import lombok.Builder;
import org.openqa.selenium.WebElement;
import com.github.saphyra.selenium.logic.domain.SeleniumAccount;
import com.github.saphyra.selenium.logic.domain.SeleniumCharacter;
import com.github.saphyra.selenium.logic.page.CommunityPage;
import com.github.saphyra.selenium.test.community.helper.MailTestHelper;
import com.github.saphyra.selenium.test.community.helper.CommunityTestHelper;
import com.github.saphyra.selenium.test.community.helper.CommunityTestInitializer;

import java.util.List;

import static com.github.saphyra.selenium.logic.domain.SeleniumCharacter.CHARACTER_NAME_PREFIX;

@Builder
public class FilterTestShouldNotShowOwnCharacters {
    private final CommunityTestInitializer communityTestInitializer;
    private final CommunityTestHelper communityTestHelper;
    private final CommunityPage communityPage;
    private final MailTestHelper mailTestHelper;

    public void testFilterShouldNotShowOwnCharacters() {
        List<SeleniumAccount> accounts = communityTestInitializer.registerAccounts(new int[]{2, 1});

        SeleniumAccount account = accounts.get(0);
        SeleniumCharacter character = account.getCharacter(0);
        communityTestHelper.goToCommunityPageOf(account, character);

        communityPage.getWriteNewMailButton().click();

        WebElement addresseeInputField = communityPage.getAddresseeInputField();
        addresseeInputField.sendKeys(CHARACTER_NAME_PREFIX);

        mailTestHelper.verifySearchResult(
            accounts.get(1).getCharacters(),
            account.getCharacters()
        );

    }
}
