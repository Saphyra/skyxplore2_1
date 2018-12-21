package selenium.cases.shop.testcase;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import selenium.helper.ShopElementSearcher;

import static org.junit.Assert.assertTrue;

public class FilterTest {
    private final ShopElementSearcher shopElementSearcher;

    public FilterTest(WebDriver driver){
        this.shopElementSearcher = new ShopElementSearcher(driver);
    }

    public void testFilters() {
        int startElementCount = shopElementSearcher.searchAllShopItems().size();

        WebElement categoryButton = shopElementSearcher.findFirstCategoryButton();
        categoryButton.click();

        int filteredElementCount = shopElementSearcher.searchAllShopItems().size();

        assertTrue(filteredElementCount < startElementCount);
    }
}
