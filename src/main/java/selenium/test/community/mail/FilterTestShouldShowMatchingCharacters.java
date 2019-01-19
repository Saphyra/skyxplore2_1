package selenium.test.community.mail;

import lombok.Builder;
import org.openqa.selenium.WebElement;
import selenium.logic.domain.SeleniumAccount;
import selenium.logic.domain.SeleniumCharacter;
import selenium.logic.page.CommunityPage;
import selenium.test.community.helper.MailTestHelper;
import selenium.test.community.helper.CommunityTestHelper;
import selenium.test.community.helper.CommunityTestInitializer;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Builder
public class FilterTestShouldShowMatchingCharacters {
    private final CommunityTestInitializer communityTestInitializer;
    private final CommunityTestHelper communityTestHelper;
    private final CommunityPage communityPage;
    private final MailTestHelper mailTestHelper;

    public void testFilterShouldShowMatchingCharacters() {
        List<SeleniumAccount> accounts = communityTestInitializer.registerAccounts(new int[]{2, 2});

        SeleniumAccount account = accounts.get(0);
        SeleniumCharacter character = account.getCharacter(0);
        communityTestHelper.goToCommunityPageOf(account, character);

        communityPage.getWriteNewMailButton().click();

        WebElement addresseeInputField = communityPage.getAddresseeInputField();
        SeleniumAccount otherAccount = accounts.get(1);
        addresseeInputField.sendKeys(otherAccount.getCharacter(0).getCharacterName());

        mailTestHelper.verifySearchResult(
            Arrays.asList(otherAccount.getCharacter(0)),
            Stream.concat(account.getCharacters().stream(), Stream.of(otherAccount.getCharacter(1))).collect(Collectors.toList())
        );

    }
}
