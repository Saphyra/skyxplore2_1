package selenium.test.characterselect;

import org.junit.Test;
import org.openqa.selenium.By;
import selenium.SeleniumTestApplication;
import selenium.logic.domain.SeleniumCharacter;
import selenium.logic.flow.CreateCharacter;
import selenium.logic.flow.Registration;
import selenium.logic.page.CharacterSelectPage;

import static org.junit.Assert.assertEquals;
import static selenium.logic.util.LinkUtil.OVERVIEW;

public class SelectCharacterTest extends SeleniumTestApplication {
    private static final String SELECTOR_CHARACTER_NAME = "td:first-child";

    private CharacterSelectPage characterSelectPage;

    @Override
    protected void init() {
        characterSelectPage = new CharacterSelectPage(driver);
    }

    @Test
    public void testSelectCharacter() {
        new Registration(driver, messageCodes).registerUser();
        SeleniumCharacter character = new CreateCharacter(driver, messageCodes).createCharacter();

        characterSelectPage.getCharacterRow(character.getCharacterName())
            .map(element -> element.findElement(By.cssSelector(SELECTOR_CHARACTER_NAME)))
            .orElseThrow(() -> new RuntimeException("CharacterName column not found."))
            .click();

        assertEquals(OVERVIEW, driver.getCurrentUrl());
    }
}
