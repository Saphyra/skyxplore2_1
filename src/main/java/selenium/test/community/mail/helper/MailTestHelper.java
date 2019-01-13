package selenium.test.community.mail.helper;

import lombok.RequiredArgsConstructor;
import selenium.logic.domain.Mail;
import selenium.logic.domain.SeleniumCharacter;
import selenium.logic.page.CommunityPage;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RequiredArgsConstructor
public class MailTestHelper {
    private final CommunityPage communityPage;

    public void verifySearchResult(List<SeleniumCharacter> shouldContain, List<SeleniumCharacter> shouldNotContain) {
        List<String> searchResult = communityPage.getAddressees();

        shouldContain.forEach(seleniumCharacter -> assertTrue(searchResult.stream().anyMatch(characterName -> characterName.equals(seleniumCharacter.getCharacterName()))));
        shouldNotContain.forEach(seleniumCharacter -> assertFalse(searchResult.stream().anyMatch(characterName -> characterName.equals(seleniumCharacter.getCharacterName()))));
    }

    public List<Mail> getReceivedMails() {
        communityPage.getReceivedMailsPageButton().click();
        return communityPage.getReceivedMails().stream()
            .map(Mail::new)
            .collect(Collectors.toList());
    }

    public List<Mail> getSentMails() {
        communityPage.getSentMailsButton().click();
        return communityPage.getSentMails().stream()
            .map(Mail::new)
            .collect(Collectors.toList());
    }
}
