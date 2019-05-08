package com.github.saphyra.selenium.test.shop.cart;

import lombok.Builder;
import com.github.saphyra.selenium.logic.domain.Category;
import com.github.saphyra.selenium.logic.validator.CartVerifier;
import com.github.saphyra.selenium.test.shop.util.ShopTestHelper;
import com.github.saphyra.selenium.test.shop.util.ShopTestInitializer;

@Builder
public class AddToCartTest {
    private static final String CHEAP_ITEM_ID_1 = "bat-01";
    private static final String CHEAP_ITEM_ID_2 = "cex-01";

    private final CartVerifier cartVerifier;
    private final ShopTestHelper shopTestHelper;
    private final ShopTestInitializer shopTestInitializer;

    public void testAddToCart() {
        shopTestInitializer.registerAndGoToShop();

        shopTestHelper.addToCart(Category.ENERGY, CHEAP_ITEM_ID_1);
        cartVerifier.verifyCosts(Category.ENERGY, CHEAP_ITEM_ID_1, 1);

        shopTestHelper.addToCart(Category.ENERGY, CHEAP_ITEM_ID_1);
        cartVerifier.verifyCosts(Category.ENERGY, CHEAP_ITEM_ID_1, 2);

        shopTestHelper.addToCart(Category.EXTENDER, CHEAP_ITEM_ID_2);
        cartVerifier.verifyCosts(Category.EXTENDER, CHEAP_ITEM_ID_2, 1);
    }
}
