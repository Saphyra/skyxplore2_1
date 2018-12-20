package selenium.cases.shop.testcase;

import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebDriver;
import selenium.cases.shop.testcase.domain.CartItem;
import selenium.cases.shop.testcase.domain.ShopItem;
import selenium.cases.shop.testcase.helper.CartVerifier;
import selenium.cases.shop.testcase.helper.CostCounter;
import selenium.cases.shop.testcase.helper.ElementSearcher;

import static org.junit.Assert.assertFalse;

@Slf4j
public class CartTest {
    private static final String CHEAP_ITEM_ID_1 = "bat-01";
    private static final String CHEAP_ITEM_ID_2 = "cex-01";
    private static final String EXPENSIVE_ITEM_ID = "cex-02";

    private final WebDriver driver;
    private final ElementSearcher elementSearcher;
    private final CartVerifier cartVerifier;
    private final CostCounter costCounter;

    public CartTest(WebDriver driver) {
        this.driver = driver;
        this.elementSearcher = new ElementSearcher(driver);
        this.costCounter = new CostCounter(driver, elementSearcher);
        this.cartVerifier = new CartVerifier(driver, elementSearcher, costCounter);
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
        ShopItem shopItem = elementSearcher.searchShopItemById(itemId);
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
        CartItem item = elementSearcher.searchCartItemById(itemId);
        item.removeFromCart();
    }

    public void testCannotAddMoreWhenTooExpensive() {
        int cost = elementSearcher.searchShopItemById(EXPENSIVE_ITEM_ID).getCost();

        do {
            addToCart(EXPENSIVE_ITEM_ID);
        } while (canAddMore(cost));

        assertFalse(elementSearcher.searchShopItemById(EXPENSIVE_ITEM_ID).getAddToCartButton().isEnabled());
    }

    private boolean canAddMore(int cost) {
        return costCounter.getCartTotalCost() + cost <= costCounter.getCurrentMoney();
    }

    public void init() {
        driver.navigate().refresh();
    }
}
