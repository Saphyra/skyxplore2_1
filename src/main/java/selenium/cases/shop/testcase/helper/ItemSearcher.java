package selenium.cases.shop.testcase.helper;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import lombok.RequiredArgsConstructor;
import selenium.cases.shop.testcase.domain.ShopItem;

@RequiredArgsConstructor
public class ItemSearcher {
    private final WebDriver driver;

    public ShopItem searchItemById(String itemId){
        return driver.findElements(By.cssSelector("#content > div")).stream()
            .map(ShopItem::new)
            .filter(shopItem -> itemId.equalsIgnoreCase(shopItem.getId()))
            .findFirst()
            .orElseThrow(()->new RuntimeException("No ShopItem found with id " + itemId));
    }
}
