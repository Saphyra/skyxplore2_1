package selenium.cases.shop.testcase;

import static org.junit.Assert.assertTrue;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import selenium.flow.BuyItem;
import selenium.flow.Navigate;

public class BuyTest {
    private static final String BOUGHT_ITEM_ID = "bat-01";

    private final WebDriver driver;
    private final Navigate navigate;
    private final BuyItem buyItem;

    public BuyTest(WebDriver driver, Navigate navigate) {
        this.driver = driver;
        this.navigate = navigate;
        this.buyItem = new BuyItem(driver);
    }

    public void init() {
        driver.navigate().refresh();
    }

    public void testBuyEquipment() {
        buyItem.buyItem(BOUGHT_ITEM_ID, 1);

        verifyItemInStorage();
    }

    private void verifyItemInStorage() {
        navigate.toEquipmentPage();

        assertTrue(BOUGHT_ITEM_ID.equalsIgnoreCase(driver.findElement(By.cssSelector("#equipmentlist div.equipmentslotcontainer:first-child div:nth-child(2) div:first-child")).getText().split(" ")[0]));
    }
}
