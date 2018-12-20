package selenium.flow;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import selenium.page.CharacterSelectPage;
import selenium.page.OverviewPage;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static selenium.util.LinkUtil.*;

public class Navigate {
    private final WebDriver driver;

    public Navigate(WebDriver driver) {
        this.driver = driver;
    }

    public void toAccountPage() {
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

    public void toShop() {
        assertEquals(OVERVIEW, driver.getCurrentUrl());
        new OverviewPage(driver).getShopButton().click();
        new WebDriverWait(driver, 10).until(ExpectedConditions.urlToBe(SHOP));
        assertEquals(SHOP, driver.getCurrentUrl());
    }

    public void toEquipmentPage() {
        String url = driver.getCurrentUrl();
        switch (url) {
            case SHOP:
                getOverviewButton().click();
                new WebDriverWait(driver, 10).until(ExpectedConditions.urlToBe(OVERVIEW));
                toEquipmentPage();
                break;
            case OVERVIEW:
                new OverviewPage(driver).getEquipmentButton().click();
                new WebDriverWait(driver, 10).until(ExpectedConditions.urlToBe(EQUIPMENT));
                break;
            default:
                throw new RuntimeException("Unknown source page: " + url);
        }
    }

    public WebElement getOverviewButton() {
        return Optional.ofNullable(driver.findElement(By.cssSelector("footer button:nth-child(2)")))
            .orElseThrow(() -> new RuntimeException("Go to Overview button not found."));
    }
}
