package selenium.test.community.mail.helper;

import lombok.RequiredArgsConstructor;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import selenium.logic.domain.Mail;
import selenium.logic.domain.SeleniumCharacter;
import selenium.logic.page.CommunityPage;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
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

    public List<Mail> getArchivedMails() {
        communityPage.getArchivedMailsPageButton().click();
        return communityPage.getArchivedMails().stream()
            .map(Mail::new)
            .collect(Collectors.toList());
    }

    public int getNumberOfUnreadMails() {
        WebElement element = communityPage.getNumberOfUnreadMails();
        return element.getText().isEmpty() ? 0 : parseNumberOfUnreadMails(element.getText());
    }

    private Integer parseNumberOfUnreadMails(String text) {
        String split1 = text.trim().substring(1);
        String split2 = split1.split("\\)")[0];
        return Integer.valueOf(split2);
    }

    public void selectBulkArchiveOption() {
        WebElement bulkSelectInput = communityPage.getBulkEditSelectInputFieldForReceivedMails();
        bulkSelectInput.click();

        bulkSelectInput.findElement(By.cssSelector("option[value='archive']")).click();

        assertEquals("archive", bulkSelectInput.getAttribute("value"));
    }

    public void selectBulkRestoreOption() {
        communityPage.getArchivedMailsPageButton().click();
        WebElement bulkRestoreInput = communityPage.getBulkRestoreInputField();
        bulkRestoreInput.click();

        bulkRestoreInput.findElement(By.cssSelector("option[value='unarchive']")).click();

        assertEquals("unarchive", bulkRestoreInput.getAttribute("value"));

    }
}
