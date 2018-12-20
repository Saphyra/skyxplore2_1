package selenium.cases.shop.testcase.helper;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import lombok.RequiredArgsConstructor;
import selenium.cases.shop.testcase.domain.CartItem;

@RequiredArgsConstructor
public class CartVerifier {
    private final WebDriver driver;
    private final ItemSearcher itemSearcher;

    public void verifyCosts(String itemId, int amount) {
        verifyAmountInCart(itemId, amount);
        verifyItemCost(itemId, amount);
        verifyCartTotalCost();
    }

    private void verifyAmountInCart(String itemId, int amount) {
        CartItem cartItem = itemSearcher.searchCartItemById(itemId);
        assertEquals(amount, cartItem.getAmount());
    }

    private void verifyItemCost(String itemId, int amount) {
        int costPerItem = itemSearcher.searchShopItemById(itemId).getCost();
        int total = costPerItem * amount;

        CartItem cartItem = itemSearcher.searchCartItemById(itemId);
        int costPerItemType = cartItem.getCostPerItem();
        assertEquals(costPerItem, costPerItemType);

        int totalCost = cartItem.getTotalCost();
        assertEquals(total, totalCost);
    }

    private void verifyCartTotalCost() {
        List<CartItem> cartItems = itemSearcher.searchAllCartItems();
        int cartCost = cartItems.stream()
            .mapToInt(CartItem::getTotalCost)
            .sum();

        int totalCartCost = Integer.valueOf(driver.findElement(By.id("cost")).getText());
        assertEquals(cartCost, totalCartCost);
    }
}
