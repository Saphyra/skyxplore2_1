package org.github.saphyra.selenium.test.shop.cart;

import static org.junit.Assert.assertFalse;

import lombok.Builder;
import org.github.saphyra.selenium.logic.domain.Category;
import org.github.saphyra.selenium.logic.helper.CostCounter;
import org.github.saphyra.selenium.logic.helper.ShopElementSearcher;
import org.github.saphyra.selenium.test.shop.util.ShopTestHelper;
import org.github.saphyra.selenium.test.shop.util.ShopTestInitializer;

@Builder
public class TooExpensiveTest {
    private static final String EXPENSIVE_ITEM_ID = "cex-02";

    private final ShopTestInitializer shopTestInitializer;
    private final ShopElementSearcher shopElementSearcher;
    private final ShopTestHelper shopTestHelper;
    private final CostCounter costCounter;

    public void testTooExpensiveItem() {
        shopTestInitializer.registerAndGoToShop();

        int cost = shopElementSearcher.searchShopItemById(Category.EXTENDER, EXPENSIVE_ITEM_ID).getCost();

        do {
            shopTestHelper.addToCart(Category.EXTENDER, EXPENSIVE_ITEM_ID);
        } while (canAddMore(cost));

        assertFalse(shopElementSearcher.searchShopItemById(Category.EXTENDER, EXPENSIVE_ITEM_ID).canAddToCart());
    }

    private boolean canAddMore(int cost) {
        return costCounter.getCartTotalCost() + cost <= costCounter.getCurrentMoney();
    }
}
