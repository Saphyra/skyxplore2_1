package selenium.logic.helper;

import lombok.RequiredArgsConstructor;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import selenium.logic.domain.CartItem;
import selenium.logic.domain.ShopItem;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class ShopElementSearcher {
    private static final String SELECTOR_CART_ITEMS = "#basket > .basketelement";
    private static final String SELECTOR_SHOP_ITEMS = "#content > .element";
    private static final String SELECTOR_FIRST_CATEGORY_BUTTON = "#menu .menuitem";
    private static final String SELECTOR_BUY_BUTTON = "#basket > button:first-of-type";

    private final WebDriver driver;

    public List<CartItem> searchAllCartItems() {
        return driver.findElements(By.cssSelector(SELECTOR_CART_ITEMS)).stream()
            .map(CartItem::new)
            .collect(Collectors.toList());
    }

    public List<ShopItem> searchAllShopItems() {
        return driver.findElements(By.cssSelector(SELECTOR_SHOP_ITEMS)).stream()
            .map(ShopItem::new)
            .collect(Collectors.toList());
    }

    public ShopItem searchShopItemById(String itemId) {
        return searchAllShopItems().stream()
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

    public WebElement findFirstCategoryButton() {
        return driver.findElements(By.cssSelector(SELECTOR_FIRST_CATEGORY_BUTTON)).stream()
            .findFirst()
            .orElseThrow(() -> new RuntimeException("Category select menu buttons not found."));
    }

    public WebElement getBuyButton() {
        return driver.findElement(By.cssSelector(SELECTOR_BUY_BUTTON));
    }
}
