package org.github.saphyra.selenium.logic.domain;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CartItem {
    private static final String SELECTOR_ITEM_NAME = ".cart-element-title span:first-child";
    private static final String SELECTOR_TITLE_AMOUNT = ".cart-element-title span:nth-child(2)";
    private static final String SELECTOR_COST_PER_ITEM = ".cart-element-price-container span:first-child";
    private static final String SELECTOR_REMOVE_FROM_CART = "button:first-of-type";
    private static final String REGEX_COST_PER_ITEM = " ";
    private static final String REGEX_GET_ID = " ";
    private static final String SELECTOR_TOTAL_COST = ".cart-element-price-container span:nth-child(4)";

    private final WebElement element;

    public String getId() {
        return element.findElement(By.cssSelector(SELECTOR_ITEM_NAME)).getText().split(REGEX_GET_ID)[0].toLowerCase();
    }

    public int getAmount() {
        return Integer.valueOf(element.findElement(By.cssSelector(SELECTOR_TITLE_AMOUNT)).getText());
    }

    public int getCostPerItem() {
        return Integer.valueOf(element.findElement(By.cssSelector(SELECTOR_COST_PER_ITEM)).getText().split(REGEX_COST_PER_ITEM)[1]);
    }

    public int getTotalCost() {
        return Integer.valueOf(element.findElement(By.cssSelector(SELECTOR_TOTAL_COST)).getText());
    }

    public void removeFromCart() {
        element.findElement(By.cssSelector(SELECTOR_REMOVE_FROM_CART)).click();
    }
}
