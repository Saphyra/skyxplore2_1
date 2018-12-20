package selenium.cases.shop.testcase.domain;

import static org.junit.Assert.assertTrue;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ShopItem {
    private final WebElement element;

    private String id;
    private WebElement addToCartButton;

    public String getId() {
        if(id == null){
            parseId();
        }

        return id;
    }

    private void parseId() {
        id = element.findElement(By.cssSelector(".elementtitle")).getText().split(" ")[0].toLowerCase();
    }

    public void addToCart() {
        WebElement addToCartButton = getAddToCartButton();
        assertTrue(addToCartButton.isEnabled());
        addToCartButton.click();
    }

    public WebElement getAddToCartButton() {
        if(addToCartButton == null){
            addToCartButton = element.findElement(By.cssSelector("div:nth-child(2) button"));
        }
        return addToCartButton;
    }

    @Override
    public String toString(){
        return element.getText();
    }
}
