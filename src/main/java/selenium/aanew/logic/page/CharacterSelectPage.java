package selenium.aanew.logic.page;

import lombok.RequiredArgsConstructor;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static selenium.aanew.logic.util.LinkUtil.CHARACTER_SELECT;

@RequiredArgsConstructor
public class CharacterSelectPage {
    private static final String ACCOUNT_BUTTON_SELECTOR = "footer button:nth-child(2)";

    private static final String SELECTOR_CHARACTERS = "#characters tr";
    private static final String SELECTOR_CHARACTER_NAME = "td:first-child";

    private static final String ELEMENT_RENAME_CHARACTER_WINDOW = "renamecharactercontainer";

    private static final String ELEMENT_NEW_CHARACTER_NAME = "newcharactername";
    private static final String ELEMENT_INVALID_NEW_CHARACTER_NAME = "invalid_newcharactername";
    private static final String ELEMENT_NEW_CHARACTER_BUTTON = "newcharacterbutton";

    private static final String ELEMENT_RENAME_CHARACTER_NAME = "renamecharacterinput";
    private static final String ELEMENT_INVALID_RENAME_CHARACTER_NAME = "invalid_renamecharactername";
    private static final String ELEMENT_RENAME_CHARACTER_BUTTON = "renamecharacterbutton";

    private final WebDriver driver;

    public WebElement getAccountPageButton() {
        assertEquals(CHARACTER_SELECT, driver.getCurrentUrl());
        return driver.findElement(By.cssSelector(ACCOUNT_BUTTON_SELECTOR));
    }

    public WebElement getRenameCharacterWindow() {
        return driver.findElement(By.id(ELEMENT_RENAME_CHARACTER_WINDOW));
    }

    public WebElement getNewCharacterNameField() {
        return driver.findElement(By.id(ELEMENT_NEW_CHARACTER_NAME));
    }

    public WebElement getCreateCharacterButton() {
        return driver.findElement(By.id(ELEMENT_NEW_CHARACTER_BUTTON));
    }

    public WebElement getInvalidNewCharacterNameField() {
        return driver.findElement(By.id(ELEMENT_INVALID_NEW_CHARACTER_NAME));
    }

    private List<WebElement> getCharacterList() {
        return driver.findElements(By.cssSelector(SELECTOR_CHARACTERS));
    }

    public Optional<WebElement> getCharacterRow(String characterName) {
        return getCharacterList().stream()
            .filter(element -> element.findElement(By.cssSelector(SELECTOR_CHARACTER_NAME))
                .getText()
                .equals(characterName)
            ).findFirst();
    }

    public boolean isCharacterExists(String characterName) {
        return getCharacterRow(characterName).isPresent();
    }

    public WebElement getRenameCharacterNameField() {
        return driver.findElement(By.id(ELEMENT_RENAME_CHARACTER_NAME));
    }

    public WebElement getInvalidRenameCharacterNameField() {
        return driver.findElement(By.id(ELEMENT_INVALID_RENAME_CHARACTER_NAME));
    }

    public WebElement getRenameCharacterButton() {
        return driver.findElement(By.id(ELEMENT_RENAME_CHARACTER_BUTTON));
    }
}
