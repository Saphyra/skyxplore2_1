package selenium.aanew.test.characterselect.renamecharacter.helper;

import lombok.RequiredArgsConstructor;
import org.openqa.selenium.By;
import selenium.aanew.logic.domain.SeleniumCharacter;
import selenium.aanew.logic.flow.CreateCharacter;
import selenium.aanew.logic.flow.Registration;
import selenium.aanew.logic.page.CharacterSelectPage;

import static org.junit.Assert.assertTrue;

@RequiredArgsConstructor
public class RenameCharacterTestHelper {
    private static final String SELECTOR_CHARACTER_NAME = "td:first-child";
    private static final String SELECTOR_CHARACTER_MODIFICATION_MENU = "td:nth-child(2)";
    private static final String SELECTOR_RENAME_CHARACTER_BUTTON = "button:first-child";

    private final Registration registration;
    private final CreateCharacter createCharacter;
    private final CharacterSelectPage characterSelectPage;

    public SeleniumCharacter initAndOpenRenamePage() {
        registration.registerUser();
        SeleniumCharacter character = createCharacter.createCharacter();
        openRenameCharacterWindow(character);
        return character;
    }

    private void openRenameCharacterWindow(SeleniumCharacter testCharacter) {
        characterSelectPage.getCharacterList().stream()
            .filter(element -> element.findElement(By.cssSelector(SELECTOR_CHARACTER_NAME))
                .getText()
                .equals(testCharacter.getCharacterName())
            )
            .map(element -> element.findElement(By.cssSelector(SELECTOR_CHARACTER_MODIFICATION_MENU)))
            .map(element -> element.findElement(By.cssSelector(SELECTOR_RENAME_CHARACTER_BUTTON)))
            .findFirst()
            .orElseThrow(() -> new RuntimeException("Character not found"))
            .click();

        assertTrue(characterSelectPage.getRenameCharacterWindow().isDisplayed());
    }
}
