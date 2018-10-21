package selenium.cases.factory.testcase;

import lombok.RequiredArgsConstructor;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

@RequiredArgsConstructor
public class SeleniumProduct {
    private static final String ELEMENT_AMOUNT_FIELD = "label input";
    private static final String ELEMENT_MATERIAL_COST = "div:nth-child(2) div:first-child";
    private static final String ELEMENT_BUILD_BUTTON = "button";
    private final WebElement webElement;

    public void fillAmountField(int amount) {
        WebElement amountField = getAmountField();
        amountField.clear();
        amountField.sendKeys(String.valueOf(amount));
    }

    private WebElement getAmountField() {
        return webElement.findElement(By.cssSelector(ELEMENT_AMOUNT_FIELD));
    }

    public int getTooExpensiveAmount() {
        String materialCost = webElement.findElement(By.cssSelector(ELEMENT_MATERIAL_COST)).getText();
        String materialCosts = materialCost.split(": ")[1];
        int cost = Integer.valueOf(materialCosts.split(" / ")[0]);
        int current = Integer.valueOf(materialCosts.split(" / ")[1]);

        return current / cost + 1;
    }

    public WebElement getBuildButton() {
        return webElement.findElement(By.cssSelector(ELEMENT_BUILD_BUTTON));
    }
}
