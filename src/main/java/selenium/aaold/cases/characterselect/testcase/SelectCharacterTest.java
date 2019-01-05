package selenium.aaold.cases.characterselect.testcase;

import lombok.Builder;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import selenium.aanew.logic.domain.SeleniumCharacter;
import selenium.aanew.logic.flow.CreateCharacter;
import selenium.aanew.logic.page.CharacterSelectPage;

import static org.junit.Assert.assertEquals;
import static selenium.aanew.logic.util.LinkUtil.OVERVIEW;
import static selenium.aanew.logic.util.ValidationUtil.validateIfPresent;

@Builder
public class SelectCharacterTest {
    private final WebDriver driver;
    private final CharacterSelectPage characterSelectPage;
    private final SeleniumCharacter testCharacter = SeleniumCharacter.create();

    public void testSelectCharacter() {
        new CreateCharacter(driver).createCharacter(testCharacter);

        validateIfPresent(characterSelectPage.getCharacterList().stream()
            .map(element -> element.findElement(By.cssSelector("td:first-child")))
            .filter(webElement -> webElement.getText().equals(testCharacter.getCharacterName()))
            .findFirst())
            .ifPresent(WebElement::click);

        assertEquals(OVERVIEW, driver.getCurrentUrl());
    }
}
