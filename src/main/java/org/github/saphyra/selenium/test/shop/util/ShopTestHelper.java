package org.github.saphyra.selenium.test.shop.util;

import lombok.RequiredArgsConstructor;
import org.github.saphyra.selenium.logic.domain.CartItem;
import org.github.saphyra.selenium.logic.domain.Category;
import org.github.saphyra.selenium.logic.domain.ShopItem;
import org.github.saphyra.selenium.logic.helper.ShopElementSearcher;

@RequiredArgsConstructor
public class ShopTestHelper {
    private final ShopElementSearcher shopElementSearcher;

    public void addToCart(Category category, String itemId) {
        ShopItem shopItem = shopElementSearcher.searchShopItemById(category, itemId);
        shopItem.addToCart();
    }

    public void removeFromCart(String itemId) {
        CartItem item = shopElementSearcher.searchCartItemById(itemId);
        item.removeFromCart();
    }
}
