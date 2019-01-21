package selenium.test.shop;

import org.junit.Test;
import org.openqa.selenium.By;
import selenium.SeleniumTestApplication;
import selenium.logic.flow.BuyItem;
import selenium.logic.flow.CreateCharacter;
import selenium.logic.flow.Navigate;
import selenium.logic.flow.Registration;
import selenium.logic.flow.SelectCharacter;
import selenium.test.shop.util.ShopTestInitializer;

import static org.junit.Assert.assertTrue;

public class BuyItemTest extends SeleniumTestApplication {
    private static final String BOUGHT_ITEM_ID = "bat-01";
    private static final String SELECTOR_STORAGE_ITEMS = "#equipmentlist div.equipmentslotcontainer:first-child div:nth-child(2) div:first-child";

    private ShopTestInitializer shopTestInitializer;
    private BuyItem buyItem;
    private Navigate navigate;

    @Override
    protected void init() {
        shopTestInitializer = new ShopTestInitializer(
            new Registration(driver, messageCodes),
            new CreateCharacter(driver),
            new SelectCharacter(driver),
            new Navigate(driver)
        );
        buyItem = new BuyItem(driver);
        navigate = new Navigate(driver);
    }

    @Test
    public void testBuyItem() {
        shopTestInitializer.registerAndGoToShop();

        buyItem.buyItem(BOUGHT_ITEM_ID, 1);

        verifyItemInStorage();
    }

    private void verifyItemInStorage() {
        navigate.toEquipmentPage();

        assertTrue(BOUGHT_ITEM_ID.equalsIgnoreCase(driver.findElement(By.cssSelector(SELECTOR_STORAGE_ITEMS)).getText().split(" ")[0]));
    }
}
