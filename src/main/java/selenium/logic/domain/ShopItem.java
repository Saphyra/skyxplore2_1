package selenium.logic.domain;

import static org.junit.Assert.assertTrue;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ShopItem {
    private static final String SELECTOR_TITLE = ".element-title";
    private static final String REGEX_ID = " ";
    private static final String SELECTOR_ADD_TO_CART_BUTTON = "div:nth-child(2) button";
    private static final String SELECTOR_COST = "div:nth-child(2) div:first-child";
    private static final String REGEX_COST = " ";

    private final WebElement element;

    public String getId() {
        return element.findElement(By.cssSelector(SELECTOR_TITLE)).getText().split(REGEX_ID)[0].toLowerCase();
    }

    public void addToCart() {
        assertTrue(canAddToCart());
        getAddToCartButton().click();
    }

    public int getCost() {
        return Integer.valueOf(element.findElement(By.cssSelector(SELECTOR_COST)).getText().split(REGEX_COST)[1]);
    }

    public boolean canAddToCart() {
        return getAddToCartButton().isEnabled();
    }

    private WebElement getAddToCartButton() {
        return element.findElement(By.cssSelector(SELECTOR_ADD_TO_CART_BUTTON));
    }
}
