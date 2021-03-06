package com.github.saphyra.selenium.logic.helper;

import com.github.saphyra.selenium.logic.domain.CartItem;
import com.github.saphyra.selenium.logic.domain.ShopItem;
import lombok.RequiredArgsConstructor;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.github.saphyra.selenium.logic.domain.Category;
import com.github.saphyra.selenium.logic.page.ShopPage;
import com.github.saphyra.selenium.logic.util.CategoryNameHelper;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class ShopElementSearcher {
    private static final String SELECTOR_CART_ITEMS = "#cart-items > .cart-element";
    private static final String SELECTOR_SHOP_ITEMS = "#content > .element";
    private static final String SELECTOR_BUY_BUTTON = "buy-items-button";

    private final WebDriver driver;
    private final CategoryNameHelper categoryNameHelper;
    private final ShopPage shopPage;

    public List<CartItem> searchAllCartItems() {
        return driver.findElements(By.cssSelector(SELECTOR_CART_ITEMS)).stream()
            .map(CartItem::new)
            .collect(Collectors.toList());
    }

    private List<ShopItem> searchAllShopItems() {
        return driver.findElements(By.cssSelector(SELECTOR_SHOP_ITEMS)).stream()
            .map(ShopItem::new)
            .collect(Collectors.toList());
    }

    public ShopItem searchShopItemById(Category category, String itemId) {
        String categoryName = categoryNameHelper.getCategoryName(category);
        shopPage.getCategoryButtonWithName(categoryName).click();
        return searchAllShopItems().stream()
            .filter(shopItem -> itemId.equalsIgnoreCase(shopItem.getId()))
            .findFirst()
            .orElseThrow(() -> new RuntimeException("No ShopItem found with id " + itemId + " in category " + category));
    }

    public CartItem searchCartItemById(String itemId) {
        return searchAllCartItems().stream()
            .filter(cartItem -> itemId.equalsIgnoreCase(cartItem.getId()))
            .findFirst()
            .orElseThrow(() -> new RuntimeException("No CartItem found with id " + itemId));
    }

    public WebElement getBuyButton() {
        return driver.findElement(By.id(SELECTOR_BUY_BUTTON));
    }
}
