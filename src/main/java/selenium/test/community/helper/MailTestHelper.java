package selenium.test.community.helper;

import lombok.RequiredArgsConstructor;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import selenium.logic.domain.Mail;
import selenium.logic.domain.SeleniumCharacter;
import selenium.logic.page.CommunityPage;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static selenium.logic.util.Util.ATTRIBUTE_VALUE;

@RequiredArgsConstructor
public class MailTestHelper {
    private static final String SELECTOR_BASE_OPTION = "option[value='%s']";
    private static final String VALUE_ARCHIVE = "archive";
    private static final String SELECTOR_ARCHIVE_OPTION = String.format(SELECTOR_BASE_OPTION, VALUE_ARCHIVE);
    private static final String VALUE_UNARCHIVE = "unarchive";
    private static final String SELECTOR_UNARCHIVE_OPTION = String.format(SELECTOR_BASE_OPTION, VALUE_UNARCHIVE);
    private static final String VALUE_DELETE = "delete";
    private static final String SELECTOR_DELETE_OPTION = String.format(SELECTOR_BASE_OPTION, VALUE_DELETE);
    private static final String VALUE_MARK_AS_READ = "markasread";
    private static final String SELECTOR_MARK_AS_READ_OPTION = String.format(SELECTOR_BASE_OPTION, VALUE_MARK_AS_READ);
    private static final String VALUE_MARK_AS_UNREAD = "markasunread";
    private static final String SELECTOR_MARK_AS_UNREAD_OPTION = String.format(SELECTOR_BASE_OPTION, VALUE_MARK_AS_UNREAD);

    private final CommunityPage communityPage;
    private final WebDriver driver;

    public void verifySearchResult(List<SeleniumCharacter> shouldContain, List<SeleniumCharacter> shouldNotContain) {
        List<String> searchResult = communityPage.getAddressees();

        shouldContain.forEach(seleniumCharacter -> assertTrue(searchResult.stream().anyMatch(characterName -> characterName.equals(seleniumCharacter.getCharacterName()))));
        shouldNotContain.forEach(seleniumCharacter -> assertFalse(searchResult.stream().anyMatch(characterName -> characterName.equals(seleniumCharacter.getCharacterName()))));
    }

    public List<Mail> getReceivedMails() {
        communityPage.getIncomingMailsPageButton().click();
        return communityPage.getIncomingMails().stream()
            .map(element -> new Mail(element, driver))
            .collect(Collectors.toList());
    }

    public List<Mail> getSentMails() {
        communityPage.getSentMailsPageButton().click();
        return communityPage.getSentMails().stream()
            .map(element -> new Mail(element, driver))
            .collect(Collectors.toList());
    }

    public List<Mail> getArchivedMails() {
        communityPage.getArchivedMailsPageButton().click();
        return communityPage.getArchivedMails().stream()
            .map(element -> new Mail(element, driver))
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
        WebElement bulkSelectInput = communityPage.getBulkEditInputFieldForReceivedMails();
        bulkSelectInput.click();

        bulkSelectInput.findElement(By.cssSelector(SELECTOR_ARCHIVE_OPTION)).click();

        assertEquals(VALUE_ARCHIVE, bulkSelectInput.getAttribute(ATTRIBUTE_VALUE));
    }

    public void selectBulkRestoreOption() {
        communityPage.getArchivedMailsPageButton().click();
        WebElement bulkRestoreInput = communityPage.getBulkEditInputFieldForArchivedMails();
        bulkRestoreInput.click();

        bulkRestoreInput.findElement(By.cssSelector(SELECTOR_UNARCHIVE_OPTION)).click();

        assertEquals(VALUE_UNARCHIVE, bulkRestoreInput.getAttribute(ATTRIBUTE_VALUE));
    }

    public void selectBulkDeleteOptionForSentMails() {
        communityPage.getSentMailsPageButton().click();
        WebElement bulkDeleteInput = communityPage.getBulkEditInputFieldForSentMails();
        bulkDeleteInput.click();

        bulkDeleteInput.findElement(By.cssSelector(SELECTOR_DELETE_OPTION)).click();

        assertEquals(VALUE_DELETE, bulkDeleteInput.getAttribute(ATTRIBUTE_VALUE));
    }

    public void selectBulkDeleteOptionForReceivedMails() {
        communityPage.getIncomingMailsPageButton().click();
        WebElement bulkDeleteInput = communityPage.getBulkEditInputFieldForReceivedMails();
        bulkDeleteInput.click();

        bulkDeleteInput.findElement(By.cssSelector(SELECTOR_DELETE_OPTION)).click();

        assertEquals(VALUE_DELETE, bulkDeleteInput.getAttribute(ATTRIBUTE_VALUE));
    }

    public void selectBulkDeleteOptionForArchivedMails() {
        communityPage.getArchivedMailsPageButton().click();
        WebElement bulkDeleteInput = communityPage.getBulkEditInputFieldForArchivedMails();
        bulkDeleteInput.click();

        bulkDeleteInput.findElement(By.cssSelector(SELECTOR_DELETE_OPTION)).click();

        assertEquals(VALUE_DELETE, bulkDeleteInput.getAttribute(ATTRIBUTE_VALUE));
    }

    public void selectBulkMarkAsReadOption() {
        communityPage.getIncomingMailsPageButton().click();
        WebElement inputField = communityPage.getBulkEditInputFieldForReceivedMails();
        inputField.click();

        inputField.findElement(By.cssSelector(SELECTOR_MARK_AS_READ_OPTION)).click();

        assertEquals(VALUE_MARK_AS_READ, inputField.getAttribute(ATTRIBUTE_VALUE));
    }

    public Mail getMail() {
        return getReceivedMails().stream()
            .findAny()
            .orElseThrow(() -> new RuntimeException("Mail not found"));
    }

    public void selectBulkMarkAsUnreadOption() {
        communityPage.getIncomingMailsPageButton().click();
        WebElement inputField = communityPage.getBulkEditInputFieldForReceivedMails();
        inputField.click();

        inputField.findElement(By.cssSelector(SELECTOR_MARK_AS_UNREAD_OPTION)).click();

        assertEquals(VALUE_MARK_AS_UNREAD, inputField.getAttribute(ATTRIBUTE_VALUE));
    }
}
