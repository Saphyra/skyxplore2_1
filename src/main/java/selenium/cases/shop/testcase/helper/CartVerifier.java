package selenium.cases.shop.testcase.helper;

import lombok.RequiredArgsConstructor;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import selenium.cases.shop.testcase.domain.CartItem;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RequiredArgsConstructor
public class CartVerifier {
    private final WebDriver driver;
    private final ElementSearcher elementSearcher;
    private final CostCounter costCounter;

    public void verifyCosts(String itemId, int amount) {
        verifyAmountInCart(itemId, amount);
        verifyItemCost(itemId, amount);
        verifyCartTotalCost();
    }

    private void verifyAmountInCart(String itemId, int amount) {
        CartItem cartItem = elementSearcher.searchCartItemById(itemId);
        assertEquals(amount, cartItem.getAmount());
    }

    private void verifyItemCost(String itemId, int amount) {
        int costPerItem = elementSearcher.searchShopItemById(itemId).getCost();
        int total = costPerItem * amount;

        CartItem cartItem = elementSearcher.searchCartItemById(itemId);
        int costPerItemType = cartItem.getCostPerItem();
        assertEquals(costPerItem, costPerItemType);

        int totalCost = cartItem.getTotalCost();
        assertEquals(total, totalCost);
    }

    private void verifyCartTotalCost() {
        int cartCost = costCounter.sumCartItemCosts();

        int totalCartCost = costCounter.getCartTotalCost();
        assertEquals(cartCost, totalCartCost);
    }

    public void verifyNotInCart(String itemId) {
        assertTrue(
            elementSearcher.searchAllCartItems().stream()
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
