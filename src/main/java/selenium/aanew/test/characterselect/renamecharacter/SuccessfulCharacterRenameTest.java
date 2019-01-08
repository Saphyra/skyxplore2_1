package selenium.aanew.test.characterselect.renamecharacter;

import lombok.Builder;
import org.openqa.selenium.WebElement;
import selenium.aanew.logic.domain.SeleniumCharacter;
import selenium.aanew.logic.page.CharacterSelectPage;
import selenium.aanew.logic.validator.NotificationValidator;
import selenium.aanew.test.characterselect.renamecharacter.helper.RenameCharacterTestHelper;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@Builder
public class SuccessfulCharacterRenameTest {
    private static final String SELECTOR_CHARACTER_NAME = "td:first-child";
    private static final String SELECTOR_CHARACTER_MODIFICATION_MENU = "td:nth-child(2)";
    private static final String SELECTOR_RENAME_CHARACTER_BUTTON = "button:first-child";

    private static final String NOTIFICATION_CHARACTER_RENAMED = "Karakter átnevezve.";

    private final RenameCharacterTestHelper renameCharacterTestHelper;
    private final CharacterSelectPage characterSelectPage;
    private final NotificationValidator notificationValidator;

    public void testSuccessfulCharacterRename() {
        SeleniumCharacter character = renameCharacterTestHelper.initAndOpenRenamePage();

        String newName = SeleniumCharacter.createRandomCharacterName();

        WebElement renameCharacterField = characterSelectPage.getRenameCharacterNameField();
        renameCharacterField.clear();
        renameCharacterField.sendKeys(newName);

        renameCharacterTestHelper.sendForm();

        notificationValidator.verifyNotificationVisibility(NOTIFICATION_CHARACTER_RENAMED);

        assertTrue(
            characterSelectPage.isCharacterExists(newName)
        );

        assertFalse(
            characterSelectPage.isCharacterExists(character.getCharacterName())
        );
    }
}
