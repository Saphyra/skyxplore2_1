package com.github.saphyra.selenium.logic.validator;

import com.github.saphyra.selenium.logic.domain.CartItem;
import com.github.saphyra.selenium.logic.helper.CostCounter;
import com.github.saphyra.selenium.logic.helper.ShopElementSearcher;
import lombok.RequiredArgsConstructor;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.github.saphyra.selenium.logic.domain.Category;
import com.github.saphyra.selenium.logic.page.ShopPage;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RequiredArgsConstructor
public class CartVerifier {
    private static final String SELECTOR_CART_ITEMS = "#basket div";

    private final WebDriver driver;
    private final ShopElementSearcher shopElementSearcher;
    private final CostCounter costCounter;
    private final ShopPage shopPage;

    public void verifyCosts(Category category, String itemId, int amount) {
        verifyAmountInCart(itemId, amount);
        verifyItemCost(category, itemId, amount);
        verifyCartTotalCost();
    }

    private void verifyAmountInCart(String itemId, int amount) {
        CartItem cartItem = shopElementSearcher.searchCartItemById(itemId);
        assertEquals(amount, cartItem.getAmount());
    }

    private void verifyItemCost(Category category, String itemId, int amount) {
        int costPerItem = shopElementSearcher.searchShopItemById(category, itemId).getCost();
        int total = costPerItem * amount;

        CartItem cartItem = shopElementSearcher.searchCartItemById(itemId);
        int costPerItemType = cartItem.getCostPerItem();
        assertEquals(costPerItem, costPerItemType);

        int totalCost = cartItem.getTotalCost();
        assertEquals(total, totalCost);
    }

    private void verifyCartTotalCost() {
        int cartCost = costCounter.sumCartItemCosts();

        verifyCartTotalCost(cartCost);
    }

    private void verifyCartTotalCost(int expectedCost){
        int totalCartCost = costCounter.getCartTotalCost();
        assertEquals(expectedCost, totalCartCost);
    }

    public void verifyNotInCart(String itemId) {
        assertTrue(
            shopElementSearcher.searchAllCartItems().stream()
                .noneMatch(cartItem -> itemId.equalsIgnoreCase(cartItem.getId()))
        );
    }

    public void verifyEmptyCart() {
        assertTrue(shopPage.getEmptyCartContainer().isDisplayed());
        List<WebElement> elements = driver.findElements(By.cssSelector(SELECTOR_CART_ITEMS));
        assertEquals(0, elements.size());
        verifyCartTotalCost(0);
    }
}
