package com.github.saphyra.selenium.logic.flow;

import static org.junit.Assert.assertEquals;
import static com.github.saphyra.selenium.logic.util.LinkUtil.CHARACTER_SELECT;
import static com.github.saphyra.selenium.logic.util.LinkUtil.OVERVIEW;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.github.saphyra.selenium.logic.domain.SeleniumCharacter;
import com.github.saphyra.selenium.logic.page.CharacterSelectPage;

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

        new WebDriverWait(driver, 10).until(ExpectedConditions.urlToBe(OVERVIEW));
    }
}
