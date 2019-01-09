package selenium.aanew.test.shop.cart;

import lombok.Builder;
import selenium.aanew.logic.validator.CartVerifier;
import selenium.aanew.test.shop.util.ShopTestHelper;
import selenium.aanew.test.shop.util.ShopTestInitializer;

@Builder
public class AddToCartTest {
    private static final String CHEAP_ITEM_ID_1 = "bat-01";
    private static final String CHEAP_ITEM_ID_2 = "cex-01";

    private final CartVerifier cartVerifier;
    private final ShopTestHelper shopTestHelper;
    private final ShopTestInitializer shopTestInitializer;

    public void testAddToCart() {
        shopTestInitializer.registerAndGoToShop();

        shopTestHelper.addToCart(CHEAP_ITEM_ID_1);
        cartVerifier.verifyCosts(CHEAP_ITEM_ID_1, 1);

        shopTestHelper.addToCart(CHEAP_ITEM_ID_1);
        cartVerifier.verifyCosts(CHEAP_ITEM_ID_1, 2);

        shopTestHelper.addToCart(CHEAP_ITEM_ID_2);
        cartVerifier.verifyCosts(CHEAP_ITEM_ID_2, 1);
    }
}
