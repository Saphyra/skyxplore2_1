package selenium.logic.validator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import lombok.RequiredArgsConstructor;
import selenium.logic.domain.CartItem;
import selenium.logic.domain.Category;
import selenium.logic.helper.CostCounter;
import selenium.logic.helper.ShopElementSearcher;

@RequiredArgsConstructor
public class CartVerifier {
    private static final String SELECTOR_CART_ITEMS = "#basket div";
    private static final String TEXT_CART_IS_EMPTY = "A kosár üres!";

    private final WebDriver driver;
    private final ShopElementSearcher shopElementSearcher;
    private final CostCounter costCounter;

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
        List<WebElement> elements = driver.findElements(By.cssSelector(SELECTOR_CART_ITEMS));
        assertEquals(1, elements.size());
        assertEquals(TEXT_CART_IS_EMPTY, elements.get(0).getText());
        verifyCartTotalCost();
    }
}
