package selenium.aanew.test.characterselect;

import org.junit.Test;
import org.openqa.selenium.By;
import selenium.aanew.SeleniumTestApplication;
import selenium.aanew.logic.domain.SeleniumCharacter;
import selenium.aanew.logic.flow.CreateCharacter;
import selenium.aanew.logic.flow.Registration;
import selenium.aanew.logic.page.CharacterSelectPage;

import static org.junit.Assert.assertEquals;
import static selenium.aanew.logic.util.LinkUtil.OVERVIEW;

public class SelectCharacterTest extends SeleniumTestApplication {
    private static final String SELECTOR_CHARACTER_NAME = "td:first-child";

    private CharacterSelectPage characterSelectPage;

    @Override
    protected void init() {
        characterSelectPage = new CharacterSelectPage(driver);
    }

    @Test
    public void testSelectCharacter() {
        new Registration(driver).registerUser();
        SeleniumCharacter character = new CreateCharacter(driver).createCharacter();

        characterSelectPage.getCharacterRow(character.getCharacterName())
            .map(element -> element.findElement(By.cssSelector(SELECTOR_CHARACTER_NAME)))
            .orElseThrow(() -> new RuntimeException("CharacterName column not found."))
            .click();

        assertEquals(OVERVIEW, driver.getCurrentUrl());
    }
}
