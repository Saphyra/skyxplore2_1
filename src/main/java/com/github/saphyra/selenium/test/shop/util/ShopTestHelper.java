package com.github.saphyra.selenium.test.shop.util;

import lombok.RequiredArgsConstructor;
import com.github.saphyra.selenium.logic.domain.CartItem;
import com.github.saphyra.selenium.logic.domain.Category;
import com.github.saphyra.selenium.logic.domain.ShopItem;
import com.github.saphyra.selenium.logic.helper.ShopElementSearcher;

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
