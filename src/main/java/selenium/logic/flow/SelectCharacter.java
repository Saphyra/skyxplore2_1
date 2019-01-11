package selenium.logic.flow;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import selenium.logic.domain.SeleniumCharacter;
import selenium.logic.page.CharacterSelectPage;

import static org.junit.Assert.assertEquals;
import static selenium.logic.util.LinkUtil.CHARACTER_SELECT;

public class SelectCharacter {
    private static final String SELECTOR_CHARACTER_NAME = "td:first-child";

    private final WebDriver driver;
    private final CharacterSelectPage characterSelectPage;

    public SelectCharacter(WebDriver driver) {
        this.driver = driver;
        this.characterSelectPage = new CharacterSelectPage(driver);
    }

    public void selectCharacter(SeleniumCharacter character) {
        assertEquals(CHARACTER_SELECT, driver.getCurrentUrl());

        characterSelectPage.getCharacterRow(character.getCharacterName())
            .map(element -> element.findElement(By.cssSelector(SELECTOR_CHARACTER_NAME)))
            .orElseThrow(() -> new RuntimeException("Character not found."))
            .click();
    }
}
