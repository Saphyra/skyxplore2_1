package selenium.cases.shop.testcase.helper;

import lombok.RequiredArgsConstructor;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import selenium.cases.shop.testcase.domain.CartItem;
import selenium.cases.shop.testcase.domain.ShopItem;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class ElementSearcher {
    private final WebDriver driver;

    public List<CartItem> searchAllCartItems() {
        return driver.findElements(By.cssSelector("#basket > .basketelement")).stream()
            .map(CartItem::new)
            .collect(Collectors.toList());
    }

    public List<ShopItem> searchAllShopItems() {
        return driver.findElements(By.cssSelector("#content > .element")).stream()
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
        return driver.findElements(By.cssSelector("#menu .menuitem")).stream()
            .findFirst()
            .orElseThrow(() -> new RuntimeException("Category select menu buttons not found."));
    }

    public WebElement getBuyButton() {
        return driver.findElement(By.cssSelector("#basket > button:first-of-type"));
    }
}
