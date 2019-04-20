package selenium.test.community.helper;

import lombok.RequiredArgsConstructor;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import selenium.logic.domain.Mail;
import selenium.logic.domain.MessageCodes;
import selenium.logic.domain.SeleniumCharacter;
import selenium.logic.page.CommunityPage;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static selenium.logic.util.Util.ATTRIBUTE_VALUE;
import static selenium.logic.util.WaitUtil.waitUntil;

@RequiredArgsConstructor
public class MailTestHelper {
    private static final String SELECTOR_BASE_OPTION = "option[value='%s']";
    private static final String VALUE_ARCHIVE = "archive";
    private static final String SELECTOR_ARCHIVE_OPTION = String.format(SELECTOR_BASE_OPTION, VALUE_ARCHIVE);
    private static final String VALUE_RESTORE = "restore";
    private static final String SELECTOR_RESTORE_OPTION = String.format(SELECTOR_BASE_OPTION, VALUE_RESTORE);
    private static final String VALUE_DELETE = "delete";
    private static final String SELECTOR_DELETE_OPTION = String.format(SELECTOR_BASE_OPTION, VALUE_DELETE);
    private static final String VALUE_MARK_AS_READ = "markasread";
    private static final String SELECTOR_MARK_AS_READ_OPTION = String.format(SELECTOR_BASE_OPTION, VALUE_MARK_AS_READ);
    private static final String VALUE_MARK_AS_UNREAD = "markasunread";
    private static final String SELECTOR_MARK_AS_UNREAD_OPTION = String.format(SELECTOR_BASE_OPTION, VALUE_MARK_AS_UNREAD);

    private final CommunityPage communityPage;
    private final WebDriver driver;
    private final MessageCodes messageCodes;

    public void verifySearchResult(List<SeleniumCharacter> shouldContain, List<SeleniumCharacter> shouldNotContain) {
        List<String> searchResult = communityPage.getAddressees();

        shouldContain.forEach(seleniumCharacter -> assertTrue(searchResult.stream().anyMatch(characterName -> characterName.equals(seleniumCharacter.getCharacterName()))));
        shouldNotContain.forEach(seleniumCharacter -> assertFalse(searchResult.stream().anyMatch(characterName -> characterName.equals(seleniumCharacter.getCharacterName()))));
    }

    public void verifyIncomingNoIncomingMails() {
        waitUntil(() -> !communityPage.isIncomingMailExists(), "Waiting until incoming mails disappear");
    }

    public void verifyNoArchivedMails() {
        waitUntil(() -> !communityPage.isArchivedMailExists(), "Waiting until archived mails disappear");
    }

    public void verifyNoSentMails() {
        waitUntil(() -> !communityPage.isSentMailExists(), "Waiting until sent mails disappear");
    }

    public List<Mail> getIncomingMails() {
        return getIncomingMails(false);
    }

    public List<Mail> getIncomingMails(boolean canBeEmpty) {
        communityPage.getIncomingMailsPageButton().click();
        return communityPage.getIncomingMails(canBeEmpty).stream()
            .map(element -> new Mail(element, driver, messageCodes))
            .collect(Collectors.toList());
    }

    public List<Mail> getSentMails() {
        communityPage.getSentMailsPageButton().click();
        return communityPage.getSentMails().stream()
            .map(element -> new Mail(element, driver, messageCodes))
            .collect(Collectors.toList());
    }

    public List<Mail> getArchivedMails() {
        communityPage.getArchivedMailsPageButton().click();
        return communityPage.getArchivedMails().stream()
            .map(element -> new Mail(element, driver, messageCodes))
            .collect(Collectors.toList());
    }

    public int getNumberOfUnreadMails() {
        return Integer.valueOf(communityPage.getNumberOfUnreadMails().getText());
    }

    public void selectBulkArchiveOption() {
        WebElement bulkSelectInput = communityPage.getBulkEditInputFieldForIncomingMails();
        bulkSelectInput.click();

        bulkSelectInput.findElement(By.cssSelector(SELECTOR_ARCHIVE_OPTION)).click();

        assertEquals(VALUE_ARCHIVE, bulkSelectInput.getAttribute(ATTRIBUTE_VALUE));
    }

    public void selectBulkRestoreOption() {
        WebElement bulkRestoreInput = communityPage.getBulkEditInputFieldForArchivedMails();
        bulkRestoreInput.click();

        bulkRestoreInput.findElement(By.cssSelector(SELECTOR_RESTORE_OPTION)).click();

        assertEquals(VALUE_RESTORE, bulkRestoreInput.getAttribute(ATTRIBUTE_VALUE));
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
        WebElement bulkDeleteInput = communityPage.getBulkEditInputFieldForIncomingMails();
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
        WebElement inputField = communityPage.getBulkEditInputFieldForIncomingMails();
        inputField.click();

        inputField.findElement(By.cssSelector(SELECTOR_MARK_AS_READ_OPTION)).click();

        assertEquals(VALUE_MARK_AS_READ, inputField.getAttribute(ATTRIBUTE_VALUE));
    }

    public Mail getMail() {
        return getIncomingMails().stream()
            .findAny()
            .orElseThrow(() -> new RuntimeException("Mail not found"));
    }

    public void selectBulkMarkAsUnreadOption() {
        communityPage.getIncomingMailsPageButton().click();
        WebElement inputField = communityPage.getBulkEditInputFieldForIncomingMails();
        inputField.click();

        inputField.findElement(By.cssSelector(SELECTOR_MARK_AS_UNREAD_OPTION)).click();

        assertEquals(VALUE_MARK_AS_UNREAD, inputField.getAttribute(ATTRIBUTE_VALUE));
    }
}
