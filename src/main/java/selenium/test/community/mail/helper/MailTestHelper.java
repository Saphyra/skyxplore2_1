package selenium.test.community.mail.helper;

import lombok.RequiredArgsConstructor;
import selenium.logic.domain.SeleniumCharacter;
import selenium.logic.page.CommunityPage;

import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RequiredArgsConstructor
public class MailTestHelper {
    private final CommunityPage communityPage;

    public void verifySearchResult(List<SeleniumCharacter> shouldContain, List<SeleniumCharacter> shouldNotContain){
        List<String> searchResult = communityPage.getAddressees();

        shouldContain.forEach(seleniumCharacter -> assertTrue(searchResult.stream().anyMatch(characterName -> characterName.equals(seleniumCharacter.getCharacterName()))));
        shouldNotContain.forEach(seleniumCharacter -> assertFalse(searchResult.stream().anyMatch(characterName -> characterName.equals(seleniumCharacter.getCharacterName()))));
    }
}
