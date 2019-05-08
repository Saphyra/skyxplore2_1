package com.github.saphyra.selenium.test.characterselect;

import org.junit.Test;
import com.github.saphyra.selenium.SeleniumTestApplication;
import com.github.saphyra.selenium.logic.flow.CreateCharacter;
import com.github.saphyra.selenium.logic.flow.Registration;
import com.github.saphyra.selenium.logic.page.CharacterSelectPage;
import com.github.saphyra.selenium.logic.validator.FieldValidator;
import com.github.saphyra.selenium.logic.validator.NotificationValidator;
import com.github.saphyra.selenium.test.characterselect.renamecharacter.ExistingCharacterNameTest;
import com.github.saphyra.selenium.test.characterselect.renamecharacter.SuccessfulCharacterRenameTest;
import com.github.saphyra.selenium.test.characterselect.renamecharacter.TooLongCharacterNameTest;
import com.github.saphyra.selenium.test.characterselect.renamecharacter.TooShortCharacterNameTest;
import com.github.saphyra.selenium.test.characterselect.renamecharacter.helper.RenameCharacterTestHelper;

import static com.github.saphyra.selenium.logic.util.LinkUtil.CHARACTER_SELECT;

public class RenameCharacterTest extends SeleniumTestApplication {
    private RenameCharacterTestHelper renameCharacterTestHelper;
    private FieldValidator fieldValidator;
    private CharacterSelectPage characterSelectPage;
    private NotificationValidator notificationValidator;

    @Override
    protected void init() {
        characterSelectPage = new CharacterSelectPage(driver);
        fieldValidator = new FieldValidator(driver, CHARACTER_SELECT);
        renameCharacterTestHelper = new RenameCharacterTestHelper(
            driver,
            new Registration(driver, messageCodes),
            new CreateCharacter(driver, messageCodes),
            characterSelectPage,
            fieldValidator
        );
        notificationValidator = new NotificationValidator(driver);
    }

    @Test
    public void testTooShortCharacterName() {
        TooShortCharacterNameTest.builder()
            .renameCharacterTestHelper(renameCharacterTestHelper)
            .characterSelectPage(characterSelectPage)
            .fieldValidator(fieldValidator)
            .messageCodes(messageCodes)
            .build()
            .testTooShortCharacterName();
    }

    @Test
    public void testTooLongCharacterName() {
        TooLongCharacterNameTest.builder()
            .renameCharacterTestHelper(renameCharacterTestHelper)
            .characterSelectPage(characterSelectPage)
            .fieldValidator(fieldValidator)
            .messageCodes(messageCodes)
            .build()
            .testTooLongCharacterName();
    }

    @Test
    public void testExistingCharacterName() {
        ExistingCharacterNameTest.builder()
            .renameCharacterTestHelper(renameCharacterTestHelper)
            .characterSelectPage(characterSelectPage)
            .fieldValidator(fieldValidator)
            .messageCodes(messageCodes)
            .build()
            .testExistingCharacterName();
    }

    @Test
    public void testSuccessfulCharacterRename() {
        SuccessfulCharacterRenameTest.builder()
            .characterSelectPage(characterSelectPage)
            .renameCharacterTestHelper(renameCharacterTestHelper)
            .notificationValidator(notificationValidator)
            .messageCodes(messageCodes)
            .build()
            .testSuccessfulCharacterRename();
    }
}
