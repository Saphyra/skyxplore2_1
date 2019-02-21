package selenium.test.shop;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.openqa.selenium.By;

import selenium.SeleniumTestApplication;
import selenium.logic.flow.BuyItem;
import selenium.logic.flow.CreateCharacter;
import selenium.logic.flow.Navigate;
import selenium.logic.flow.Registration;
import selenium.logic.flow.SelectCharacter;
import selenium.test.shop.util.ShopTestInitializer;

public class BuyItemTest extends SeleniumTestApplication {
    private static final String BOUGHT_ITEM_ID = "bat-01";
    private static final String SELECTOR_STORAGE_ITEMS = "#equipment-list .equipment-list-element span:first-child";

    private ShopTestInitializer shopTestInitializer;
    private BuyItem buyItem;
    private Navigate navigate;

    @Override
    protected void init() {
        shopTestInitializer = new ShopTestInitializer(
            new Registration(driver, messageCodes),
            new CreateCharacter(driver, messageCodes),
            new SelectCharacter(driver),
            new Navigate(driver)
        );
        buyItem = new BuyItem(driver, locale, messageCodes);
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

        String boughtItemName = driver.findElement(By.cssSelector(SELECTOR_STORAGE_ITEMS)).getText();
        assertTrue(BOUGHT_ITEM_ID.equalsIgnoreCase(boughtItemName.split(" ")[0]));
    }
}
