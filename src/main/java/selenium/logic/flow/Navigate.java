package selenium.logic.flow;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import selenium.logic.page.CharacterSelectPage;
import selenium.logic.page.OverviewPage;
import selenium.logic.util.LocatorUtil;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static selenium.logic.util.LinkUtil.*;

public class Navigate {
    private static final String SELECTOR_OVERVIEW_BUTTON = "footer button:nth-child(2)";
    private static final String SELECTOR_COMMUNITY_PAGE_BUTTON = "footer button:nth-child(3)";

    private final WebDriver driver;

    public Navigate(WebDriver driver) {
        this.driver = driver;
    }

    public void toAccountPage() {
        new CharacterSelectPage(driver).getAccountPageButton().click();

        verifyURL(ACCOUNT);
    }

    public void toFactory() {
        assertEquals(OVERVIEW, driver.getCurrentUrl());
        new OverviewPage(driver).getFactoryButton().click();

        verifyURL(FACTORY);
    }

    public void toShop() {
        new WebDriverWait(driver, 10).until(ExpectedConditions.urlToBe(OVERVIEW));
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

    private WebElement getOverviewButton() {
        return Optional.ofNullable(driver.findElement(By.cssSelector(SELECTOR_OVERVIEW_BUTTON)))
            .orElseThrow(() -> new RuntimeException("Go to Overview button not found."));
    }

    void toIndexPage() {
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
        Optional.ofNullable(driver.findElement(By.cssSelector(SELECTOR_COMMUNITY_PAGE_BUTTON)))
            .orElseThrow(() -> new RuntimeException("Community button not found."))
            .click();

        verifyURL(COMMUNITY);
    }

    private void verifyURL(String url) {
        new WebDriverWait(driver, 10).until(ExpectedConditions.urlToBe(url));
        assertEquals(url, driver.getCurrentUrl());
    }
}
