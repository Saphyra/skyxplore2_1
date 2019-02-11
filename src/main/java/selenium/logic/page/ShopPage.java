package selenium.logic.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ShopPage {
    private static final String SELECTOR_CATEGORY_BUTTONS = ".menu-child .button";
    private static final String SELECTOR_EMPTY_CART = "empty-cart";
    private final WebDriver driver;

    public WebElement getCategoryButtonWithName(String categoryName) {
        return driver.findElements(By.cssSelector(SELECTOR_CATEGORY_BUTTONS)).stream()
            .filter(element -> element.getText().equals(categoryName))
            .findAny()
            .orElseThrow(()->new RuntimeException("Category button not found with categoryName " + categoryName));
    }

    public WebElement getEmptyCartContainer() {
        return driver.findElement(By.id(SELECTOR_EMPTY_CART));
    }
}
