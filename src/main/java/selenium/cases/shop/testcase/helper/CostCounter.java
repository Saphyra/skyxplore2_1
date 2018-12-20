package selenium.cases.shop.testcase.helper;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import lombok.RequiredArgsConstructor;
import selenium.cases.shop.testcase.domain.CartItem;

@RequiredArgsConstructor
public class CostCounter {
    private final WebDriver driver;
    private final ElementSearcher elementSearcher;

    public int getCurrentMoney(){
        return Integer.valueOf(driver.findElement(By.id("money")).getText());
    }

    public int getCartTotalCost() {
        return Integer.valueOf(driver.findElement(By.id("cost")).getText());
    }

    public int sumCartItemCosts() {
        return elementSearcher.searchAllCartItems().stream()
            .mapToInt(CartItem::getTotalCost)
            .sum();
    }
}
