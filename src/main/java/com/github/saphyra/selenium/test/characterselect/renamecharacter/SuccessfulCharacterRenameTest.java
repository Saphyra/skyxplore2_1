package com.github.saphyra.selenium.test.characterselect.renamecharacter;

import com.github.saphyra.selenium.logic.domain.SeleniumCharacter;
import com.github.saphyra.selenium.logic.validator.NotificationValidator;
import lombok.Builder;
import org.openqa.selenium.WebElement;

import com.github.saphyra.selenium.logic.page.CharacterSelectPage;
import com.github.saphyra.selenium.test.characterselect.renamecharacter.helper.RenameCharacterTestHelper;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@Builder
public class SuccessfulCharacterRenameTest {
    private static final String SELECTOR_CHARACTER_NAME = "td:first-child";
    private static final String SELECTOR_CHARACTER_MODIFICATION_MENU = "td:nth-child(2)";
    private static final String SELECTOR_RENAME_CHARACTER_BUTTON = "button:first-child";

    private static final String MESSAGE_CODE_CHARACTER_RENAMED = "CHARACTER_RENAMED";

    private final RenameCharacterTestHelper renameCharacterTestHelper;
    private final CharacterSelectPage characterSelectPage;
    private final NotificationValidator notificationValidator;
    private final MessageCodes messageCodes;

    public void testSuccessfulCharacterRename() {
        SeleniumCharacter character = renameCharacterTestHelper.initAndOpenRenamePage();

        String newName = SeleniumCharacter.createRandomCharacterName();

        WebElement renameCharacterField = characterSelectPage.getRenameCharacterNameField();
        renameCharacterField.clear();
        renameCharacterField.sendKeys(newName);

        renameCharacterTestHelper.sendForm();

        notificationValidator.verifyNotificationVisibility(messageCodes.get(MESSAGE_CODE_CHARACTER_RENAMED));

        assertTrue(
            characterSelectPage.isCharacterExists(newName)
        );

        assertFalse(
            characterSelectPage.isCharacterExists(character.getCharacterName())
        );
    }
}
