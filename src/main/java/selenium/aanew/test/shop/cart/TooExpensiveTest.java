package selenium.aanew.test.shop.cart;

import lombok.Builder;
import selenium.aanew.logic.helper.CostCounter;
import selenium.aanew.logic.helper.ShopElementSearcher;
import selenium.aanew.test.shop.util.ShopTestHelper;
import selenium.aanew.test.shop.util.ShopTestInitializer;

import static org.junit.Assert.assertFalse;

@Builder
public class TooExpensiveTest {
    private static final String EXPENSIVE_ITEM_ID = "cex-02";

    private final ShopTestInitializer shopTestInitializer;
    private final ShopElementSearcher shopElementSearcher;
    private final ShopTestHelper shopTestHelper;
    private final CostCounter costCounter;

    public void testTooExpensiveItem() {
        shopTestInitializer.registerAndGoToShop();

        int cost = shopElementSearcher.searchShopItemById(EXPENSIVE_ITEM_ID).getCost();

        do {
            shopTestHelper.addToCart(EXPENSIVE_ITEM_ID);
        } while (canAddMore(cost));

        assertFalse(shopElementSearcher.searchShopItemById(EXPENSIVE_ITEM_ID).canAddToCart());
    }

    private boolean canAddMore(int cost) {
        return costCounter.getCartTotalCost() + cost <= costCounter.getCurrentMoney();
    }
}
