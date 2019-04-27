package selenium.test.shop.util;

import lombok.RequiredArgsConstructor;
import selenium.logic.domain.CartItem;
import selenium.logic.domain.Category;
import selenium.logic.domain.ShopItem;
import selenium.logic.helper.ShopElementSearcher;

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
