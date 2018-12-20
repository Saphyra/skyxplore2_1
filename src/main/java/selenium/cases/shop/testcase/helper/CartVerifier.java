package selenium.cases.shop.testcase.helper;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

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

    public void verifyNotInCart(String itemId) {
        assertTrue(
            itemSearcher.searchAllCartItems().stream()
                .noneMatch(cartItem -> itemId.equalsIgnoreCase(cartItem.getId()))
        );
    }

    public void verifyEmptyCart() {
        List<WebElement> elements = driver.findElements(By.cssSelector("#basket div"));
        assertEquals(1, elements.size());
        assertEquals("A kosár üres!", elements.get(0).getText());
        verifyCartTotalCost();
    }
}
