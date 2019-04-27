package org.github.saphyra.selenium.logic.page;

import lombok.RequiredArgsConstructor;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import static org.github.saphyra.selenium.logic.util.WaitUtil.getWithWait;

@RequiredArgsConstructor
public class ShopPage {
    private static final String SELECTOR_CATEGORY_BUTTONS = ".menu-child .button";
    private static final String SELECTOR_EMPTY_CART = "empty-cart";
    private final WebDriver driver;

    public WebElement getCategoryButtonWithName(String categoryName) {
        return getWithWait(
            () -> driver.findElements(By.cssSelector(SELECTOR_CATEGORY_BUTTONS)).stream()
                .filter(element -> element.getText().equals(categoryName))
                .findAny(),
            "Querying category with name " + categoryName)
            .orElseThrow(() -> new RuntimeException("Category button not found with categoryName " + categoryName));
    }

    public WebElement getEmptyCartContainer() {
        return driver.findElement(By.id(SELECTOR_EMPTY_CART));
    }
}
