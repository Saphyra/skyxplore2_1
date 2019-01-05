package selenium.aaold.cases.shop.testcase;

import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebDriver;
import selenium.aanew.logic.domain.CartItem;
import selenium.aanew.logic.domain.ShopItem;
import selenium.aanew.logic.validator.CartVerifier;
import selenium.aanew.logic.helper.CostCounter;
import selenium.aanew.logic.helper.ShopElementSearcher;

import static org.junit.Assert.assertFalse;

@Slf4j
public class CartTest {
    private static final String CHEAP_ITEM_ID_1 = "bat-01";
    private static final String CHEAP_ITEM_ID_2 = "cex-01";
    private static final String EXPENSIVE_ITEM_ID = "cex-02";

    private final WebDriver driver;
    private final ShopElementSearcher shopElementSearcher;
    private final CartVerifier cartVerifier;
    private final CostCounter costCounter;

    public CartTest(WebDriver driver) {
        this.driver = driver;
        this.shopElementSearcher = new ShopElementSearcher(driver);
        this.costCounter = new CostCounter(driver, shopElementSearcher);
        this.cartVerifier = new CartVerifier(driver, shopElementSearcher, costCounter);
    }

    public void testAddToCart() {
        addToCart(CHEAP_ITEM_ID_1);
        cartVerifier.verifyCosts(CHEAP_ITEM_ID_1, 1);

        addToCart(CHEAP_ITEM_ID_1);
        cartVerifier.verifyCosts(CHEAP_ITEM_ID_1, 2);

        addToCart(CHEAP_ITEM_ID_2);
        cartVerifier.verifyCosts(CHEAP_ITEM_ID_2, 1);
    }

    private void addToCart(String itemId) {
        ShopItem shopItem = shopElementSearcher.searchShopItemById(itemId);
        shopItem.addToCart();
    }

    public void testRemoveFromCart() {
        removeFromCart(CHEAP_ITEM_ID_1);
        cartVerifier.verifyCosts(CHEAP_ITEM_ID_1, 1);

        removeFromCart(CHEAP_ITEM_ID_1);
        cartVerifier.verifyNotInCart(CHEAP_ITEM_ID_1);

        removeFromCart(CHEAP_ITEM_ID_2);
        cartVerifier.verifyNotInCart(CHEAP_ITEM_ID_2);

        cartVerifier.verifyEmptyCart();
    }

    private void removeFromCart(String itemId) {
        CartItem item = shopElementSearcher.searchCartItemById(itemId);
        item.removeFromCart();
    }

    public void testCannotAddMoreWhenTooExpensive() {
        int cost = shopElementSearcher.searchShopItemById(EXPENSIVE_ITEM_ID).getCost();

        do {
            addToCart(EXPENSIVE_ITEM_ID);
        } while (canAddMore(cost));

        assertFalse(shopElementSearcher.searchShopItemById(EXPENSIVE_ITEM_ID).canAddToCart());
    }

    private boolean canAddMore(int cost) {
        return costCounter.getCartTotalCost() + cost <= costCounter.getCurrentMoney();
    }

    public void init() {
        driver.navigate().refresh();
    }
}
