package com.github.saphyra.selenium.logic.helper;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.github.saphyra.selenium.logic.domain.CartItem;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CostCounter {
    private static final String SELECTOR_CURRENT_MONEY = "money";
    private static final String SELECTOR_CART_TOTAL_COST = "cart-cost";

    private final WebDriver driver;
    private final ShopElementSearcher shopElementSearcher;

    public int getCurrentMoney() {
        return Integer.valueOf(driver.findElement(By.id(SELECTOR_CURRENT_MONEY)).getText());
    }

    public int getCartTotalCost() {
        return Integer.valueOf(driver.findElement(By.id(SELECTOR_CART_TOTAL_COST)).getText());
    }

    public int sumCartItemCosts() {
        return shopElementSearcher.searchAllCartItems().stream()
            .mapToInt(CartItem::getTotalCost)
            .sum();
    }
}
