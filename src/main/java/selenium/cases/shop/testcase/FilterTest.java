package selenium.cases.shop.testcase;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import selenium.cases.shop.testcase.helper.ElementSearcher;

import static org.junit.Assert.assertTrue;

public class FilterTest {
    private final ElementSearcher elementSearcher;

    public FilterTest(WebDriver driver){
        this.elementSearcher = new ElementSearcher(driver);
    }

    public void testFilters() {
        int startElementCount = elementSearcher.searchAllShopItems().size();

        WebElement categoryButton = elementSearcher.findFirstCategoryButton();
        categoryButton.click();

        int filteredElementCount = elementSearcher.searchAllShopItems().size();

        assertTrue(filteredElementCount < startElementCount);
    }
}
