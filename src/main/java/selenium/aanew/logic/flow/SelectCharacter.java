package selenium.aanew.logic.flow;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import selenium.aanew.logic.domain.SeleniumCharacter;
import selenium.aanew.logic.page.CharacterSelectPage;

import static org.junit.Assert.assertEquals;
import static selenium.aanew.logic.util.LinkUtil.CHARACTER_SELECT;
import static selenium.aanew.logic.util.Util.validateIfPresent;

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
        validateIfPresent(characterSelectPage.getCharacterList().stream()
            .map(element -> element.findElement(By.cssSelector(SELECTOR_CHARACTER_NAME)))
            .filter(webElement -> webElement.getText().equals(character.getCharacterName()))
            .findFirst())
            .ifPresent(WebElement::click);
    }
}
