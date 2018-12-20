package selenium.cases.shop.testcase.domain;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CartItem {
    private final WebElement element;

    private String id;
    private String titleText;
    private WebElement costElement;
    private Integer costPerItem;
    private Integer totalCost;

    public String getId() {
        if (id == null) {
            parseId();
        }
        return id;
    }

    private void parseId() {
        id = getTitleText().split(" ")[0].toLowerCase();
    }

    private String getTitleText() {
        if (titleText == null) {
            titleText = element.findElement(By.cssSelector(".basketelementtitle")).getText();
        }
        return titleText;
    }

    public int getAmount() {
        return Integer.valueOf(getTitleText().split(" x ")[1]);
    }

    public int getCostPerItem() {
        if (costPerItem == null) {
            costPerItem = parseCostPerItem();
        }
        return costPerItem;
    }

    private Integer parseCostPerItem() {
        return Integer.valueOf(getCostElement().getText().split(" ")[1]);
    }

    private WebElement getCostElement() {
        if (costElement == null) {
            costElement = element.findElement(By.cssSelector("div:nth-child(2)"));
        }
        return costElement;
    }

    public int getTotalCost() {
        if(totalCost == null){
            totalCost = parseTotalCost();
        }
        return totalCost;
    }

    private Integer parseTotalCost() {
        return Integer.valueOf(getCostElement().getText().split(" = ")[1]);
    }

    @Override
    public String toString() {
        return element.getText();
    }

    public void removeFromCart() {
        element.findElement(By.cssSelector("button:first-of-type")).click();
    }
}
