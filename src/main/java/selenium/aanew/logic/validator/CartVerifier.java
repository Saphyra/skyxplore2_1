package selenium.aanew.logic.validator;

import lombok.RequiredArgsConstructor;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import selenium.aanew.logic.domain.CartItem;
import selenium.aanew.logic.helper.CostCounter;
import selenium.aanew.logic.helper.ShopElementSearcher;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RequiredArgsConstructor
public class CartVerifier {
    private final WebDriver driver;
    private final ShopElementSearcher shopElementSearcher;
    private final CostCounter costCounter;

    public void verifyCosts(String itemId, int amount) {
        verifyAmountInCart(itemId, amount);
        verifyItemCost(itemId, amount);
        verifyCartTotalCost();
    }

    private void verifyAmountInCart(String itemId, int amount) {
        CartItem cartItem = shopElementSearcher.searchCartItemById(itemId);
        assertEquals(amount, cartItem.getAmount());
    }

    private void verifyItemCost(String itemId, int amount) {
        int costPerItem = shopElementSearcher.searchShopItemById(itemId).getCost();
        int total = costPerItem * amount;

        CartItem cartItem = shopElementSearcher.searchCartItemById(itemId);
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
            shopElementSearcher.searchAllCartItems().stream()
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
