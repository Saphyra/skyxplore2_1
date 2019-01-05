package selenium.aanew.logic.flow;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import selenium.aanew.logic.page.CharacterSelectPage;
import selenium.aanew.logic.page.OverviewPage;
import selenium.aanew.logic.util.LocatorUtil;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static selenium.aanew.logic.util.LinkUtil.*;

public class Navigate {
    private final WebDriver driver;

    public Navigate(WebDriver driver) {
        this.driver = driver;
    }

    public void toAccountPage() {
        new CharacterSelectPage(driver).getAccountPageButton().click();

        verifyURL(ACCOUNT);
    }

    public void toCharacterSelectPage() {
        assertEquals(OVERVIEW, driver.getCurrentUrl());
        new OverviewPage(driver).getCharacterSelectPageButton().click();

        verifyURL(CHARACTER_SELECT);
    }

    public void toFactory() {
        assertEquals(OVERVIEW, driver.getCurrentUrl());
        new OverviewPage(driver).getFactoryButton().click();

        verifyURL(FACTORY);
    }

    public void toShop() {
        assertEquals(OVERVIEW, driver.getCurrentUrl());
        new OverviewPage(driver).getShopButton().click();

        verifyURL(SHOP);
    }

    public void toEquipmentPage() {
        String url = driver.getCurrentUrl();
        switch (url) {
            case SHOP:
                getOverviewButton().click();
                verifyURL(OVERVIEW);
                toEquipmentPage();
                break;
            case OVERVIEW:
                new OverviewPage(driver).getEquipmentButton().click();
                break;
            default:
                throw new RuntimeException("Unknown source page: " + url);
        }

        new WebDriverWait(driver, 10).until(ExpectedConditions.urlToBe(EQUIPMENT));
        assertEquals(EQUIPMENT, driver.getCurrentUrl());
    }

    public WebElement getOverviewButton() {
        return Optional.ofNullable(driver.findElement(By.cssSelector("footer button:nth-child(2)")))
            .orElseThrow(() -> new RuntimeException("Go to Overview button not found."));
    }

    public void toIndexPage() {
        Optional<WebElement> logoutButton = LocatorUtil.getLogoutButton(driver);
        if (logoutButton.isPresent()) {
            logoutButton.get().click();
        } else {
            driver.navigate().to(HOST);
        }

        verifyURL(HOST);
    }

    public void toCommunityPage() {
        assertEquals(OVERVIEW, driver.getCurrentUrl());
        Optional.ofNullable(driver.findElement(By.cssSelector("footer button:nth-child(3)")))
            .orElseThrow(() -> new RuntimeException("Community button not found."))
            .click();

        verifyURL(COMMUNITY);
    }

    private void verifyURL(String url){
        new WebDriverWait(driver, 10).until(ExpectedConditions.urlToBe(url));
        assertEquals(url, driver.getCurrentUrl());
    }
}
