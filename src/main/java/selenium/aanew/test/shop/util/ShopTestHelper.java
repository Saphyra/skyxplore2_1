package selenium.aanew.test.shop.util;

import lombok.RequiredArgsConstructor;
import selenium.aanew.logic.domain.ShopItem;
import selenium.aanew.logic.helper.ShopElementSearcher;

@RequiredArgsConstructor
public class ShopTestHelper {
    private final ShopElementSearcher shopElementSearcher;

    public void addToCart(String itemId){
        ShopItem shopItem = shopElementSearcher.searchShopItemById(itemId);
        shopItem.addToCart();
    }
}
