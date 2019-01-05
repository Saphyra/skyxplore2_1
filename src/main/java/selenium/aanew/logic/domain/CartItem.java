package selenium.aanew.logic.domain;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CartItem {
    private static final String SELECTOR_TITLE = ".basketelementtitle";
    private static final String SELECTOR_COST = "div:nth-child(2)";
    private static final String SELECTOR_REMOVE_FROM_CART = "button:first-of-type";
    private static final String REGEX_TOTAL_COST = " = ";
    private static final String REGEX_COST_PER_ITEM = " ";
    private static final String REGEX_AMOUNT = " x ";
    private static final String REGEX_GET_ID = " ";

    private final WebElement element;

    public String getId() {
        return getTitleText().split(REGEX_GET_ID)[0].toLowerCase();
    }

    private String getTitleText() {
        return element.findElement(By.cssSelector(SELECTOR_TITLE)).getText();
    }

    public int getAmount() {
        return Integer.valueOf(getTitleText().split(REGEX_AMOUNT)[1]);
    }

    public int getCostPerItem() {
        return Integer.valueOf(getCostElement().getText().split(REGEX_COST_PER_ITEM)[1]);
    }

    private WebElement getCostElement() {
        return element.findElement(By.cssSelector(SELECTOR_COST));
    }

    public int getTotalCost() {
        return Integer.valueOf(getCostElement().getText().split(REGEX_TOTAL_COST)[1]);
    }

    public void removeFromCart() {
        element.findElement(By.cssSelector(SELECTOR_REMOVE_FROM_CART)).click();
    }
}
