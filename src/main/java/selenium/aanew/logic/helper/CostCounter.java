package selenium.aanew.logic.helper;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import lombok.RequiredArgsConstructor;
import selenium.aanew.logic.domain.CartItem;

@RequiredArgsConstructor
public class CostCounter {
    private static final String SELECTOR_CURRENT_MONEY = "money";
    private static final String SELECTOR_CART_TOTAL_COST = "cost";

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
