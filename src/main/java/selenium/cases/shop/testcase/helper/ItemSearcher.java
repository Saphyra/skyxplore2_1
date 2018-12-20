package selenium.cases.shop.testcase.helper;

import java.util.List;
import java.util.stream.Collectors;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import lombok.RequiredArgsConstructor;
import selenium.cases.shop.testcase.domain.CartItem;
import selenium.cases.shop.testcase.domain.ShopItem;

@RequiredArgsConstructor
public class ItemSearcher {
    private final WebDriver driver;

    public List<CartItem> searchAllCartItems() {
        return driver.findElements(By.cssSelector("#basket > .basketelement")).stream()
            .map(CartItem::new)
            .collect(Collectors.toList());
    }

    public ShopItem searchShopItemById(String itemId) {
        return driver.findElements(By.cssSelector("#content > .element")).stream()
            .map(ShopItem::new)
            .filter(shopItem -> itemId.equalsIgnoreCase(shopItem.getId()))
            .findFirst()
            .orElseThrow(() -> new RuntimeException("No ShopItem found with id " + itemId));
    }

    public CartItem searchCartItemById(String itemId) {
        return searchAllCartItems().stream()
            .filter(cartItem -> itemId.equalsIgnoreCase(cartItem.getId()))
            .findFirst()
            .orElseThrow(() -> new RuntimeException("No CartItem found with id " + itemId));
    }

}
