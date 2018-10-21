package selenium.flow;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import selenium.page.CharacterSelectPage;
import selenium.page.OverviewPage;

import static org.junit.Assert.assertEquals;
import static selenium.util.LinkUtil.*;

public class Navigate {
    private final WebDriver driver;

    public Navigate(WebDriver driver) {
        this.driver = driver;
    }

    public void toAccountPage(){
        new CharacterSelectPage(driver).getAccountPageButton().click();
        assertEquals(ACCOUNT, driver.getCurrentUrl());
    }

    public void toCharacterSelectPage() {
        assertEquals(OVERVIEW, driver.getCurrentUrl());
        new OverviewPage(driver).getCharacterSelectPageButton().click();
        new WebDriverWait(driver, 10).until(ExpectedConditions.urlToBe(CHARACTER_SELECT));
        assertEquals(CHARACTER_SELECT, driver.getCurrentUrl());
    }

    public void toFactory() {
        assertEquals(OVERVIEW, driver.getCurrentUrl());
        new OverviewPage(driver).getFactoryButton().click();
        new WebDriverWait(driver, 10).until(ExpectedConditions.urlToBe(FACTORY));
        assertEquals(FACTORY, driver.getCurrentUrl());
    }
}
