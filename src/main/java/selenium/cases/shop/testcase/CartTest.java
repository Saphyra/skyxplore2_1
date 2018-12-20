package selenium.cases.shop.testcase;

import org.openqa.selenium.WebDriver;

import lombok.extern.slf4j.Slf4j;
import selenium.cases.shop.testcase.domain.CartItem;
import selenium.cases.shop.testcase.domain.ShopItem;
import selenium.cases.shop.testcase.helper.CartVerifier;
import selenium.cases.shop.testcase.helper.ItemSearcher;

@Slf4j
public class CartTest {
    private static final String CHEAP_ITEM_ID_1 = "bat-01";
    private static final String CHEAP_ITEM_ID_2 = "cex-01";

    private final WebDriver driver;
    private final ItemSearcher itemSearcher;
    private final CartVerifier cartVerifier;

    public CartTest(WebDriver driver) {
        this.driver = driver;
        this.itemSearcher = new ItemSearcher(driver);
        this.cartVerifier = new CartVerifier(driver, itemSearcher);
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
        ShopItem shopItem = itemSearcher.searchShopItemById(itemId);
        shopItem.addToCart();
    }

    public void testRemoveFromCart() {
        //Remove one
        //Remove more

        removeFromCart(CHEAP_ITEM_ID_1);
        cartVerifier.verifyCosts(CHEAP_ITEM_ID_1, 1);

        removeFromCart(CHEAP_ITEM_ID_1);
        cartVerifier.verifyNotInCart(CHEAP_ITEM_ID_1);

        removeFromCart(CHEAP_ITEM_ID_2);
        cartVerifier.verifyNotInCart(CHEAP_ITEM_ID_2);

        cartVerifier.verifyEmptyCart();
        //Verify: item is in cart with the correct amount
        //Verify: total cost displayed properly
        //Verify: empty cart message appears when empty
    }

    private void removeFromCart(String itemId) {
        CartItem item = itemSearcher.searchCartItemById(itemId);
        item.removeFromCart();
    }

    public void cannotAddMoreWhenTooExpensive() {
        //Add one

        //Verify: item is in cart
        //Verify: cannot add more
    }
}
