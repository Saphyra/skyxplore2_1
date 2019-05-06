package org.github.saphyra.selenium.test.shop;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.github.saphyra.selenium.SeleniumTestApplication;
import org.github.saphyra.selenium.logic.domain.Category;
import org.github.saphyra.selenium.logic.flow.BuyItem;
import org.github.saphyra.selenium.logic.flow.CreateCharacter;
import org.github.saphyra.selenium.logic.flow.Navigate;
import org.github.saphyra.selenium.logic.flow.Registration;
import org.github.saphyra.selenium.logic.flow.SelectCharacter;
import org.github.saphyra.selenium.test.shop.util.ShopTestInitializer;

import java.util.Optional;

import static org.junit.Assert.assertTrue;
import static org.github.saphyra.selenium.logic.util.WaitUtil.getWithWait;

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

        buyItem.buyItem(BOUGHT_ITEM_ID, Category.ENERGY);

        verifyItemInStorage();
    }

    private void verifyItemInStorage() {
        navigate.toEquipmentPage();
        Optional<WebElement> boughtItem = getWithWait(
            () -> driver.findElements(By.cssSelector(SELECTOR_STORAGE_ITEMS)).stream().findFirst(),
            "Querying item in storage"
        );
        String boughtItemName = boughtItem.orElseThrow(()-> new RuntimeException("Item not found in storage."))
            .getText();
        assertTrue(BOUGHT_ITEM_ID.equalsIgnoreCase(boughtItemName.split(" ")[0]));
    }
}
