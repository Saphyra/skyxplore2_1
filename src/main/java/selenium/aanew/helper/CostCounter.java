package selenium.aanew.helper;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import lombok.RequiredArgsConstructor;
import selenium.aanew.domain.CartItem;

@RequiredArgsConstructor
public class CostCounter {
    private final WebDriver driver;
    private final ShopElementSearcher shopElementSearcher;

    public int getCurrentMoney(){
        return Integer.valueOf(driver.findElement(By.id("money")).getText());
    }

    public int getCartTotalCost() {
        return Integer.valueOf(driver.findElement(By.id("cost")).getText());
    }

    public int sumCartItemCosts() {
        return shopElementSearcher.searchAllCartItems().stream()
            .mapToInt(CartItem::getTotalCost)
            .sum();
    }
}
