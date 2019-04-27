package org.github.saphyra.selenium.test.characterselect;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.github.saphyra.selenium.SeleniumTestApplication;
import org.github.saphyra.selenium.logic.domain.SeleniumCharacter;
import org.github.saphyra.selenium.logic.flow.CreateCharacter;
import org.github.saphyra.selenium.logic.flow.Registration;
import org.github.saphyra.selenium.logic.page.CharacterSelectPage;

import static org.junit.Assert.assertEquals;
import static org.github.saphyra.selenium.logic.util.LinkUtil.OVERVIEW;

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

        new WebDriverWait(driver, 10).until(ExpectedConditions.urlToBe(OVERVIEW));
        assertEquals(OVERVIEW, driver.getCurrentUrl());
    }
}
