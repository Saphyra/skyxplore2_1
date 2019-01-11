package selenium.test.shop;

import org.junit.Test;
import org.openqa.selenium.WebElement;
import selenium.SeleniumTestApplication;
import selenium.logic.flow.CreateCharacter;
import selenium.logic.flow.Navigate;
import selenium.logic.flow.Registration;
import selenium.logic.flow.SelectCharacter;
import selenium.logic.helper.ShopElementSearcher;
import selenium.test.shop.util.ShopTestInitializer;

import static org.junit.Assert.assertTrue;

public class FilterTest extends SeleniumTestApplication {
    private ShopTestInitializer shopTestInitializer;
    private ShopElementSearcher shopElementSearcher;

    @Override
    protected void init() {
        shopTestInitializer = new ShopTestInitializer(
            new Registration(driver),
            new CreateCharacter(driver),
            new SelectCharacter(driver),
            new Navigate(driver)
        );

        shopElementSearcher = new ShopElementSearcher(driver);
    }

    @Test
    public void testFilter() {
        shopTestInitializer.registerAndGoToShop();

        int startElementCount = shopElementSearcher.searchAllShopItems().size();

        WebElement categoryButton = shopElementSearcher.findFirstCategoryButton();
        categoryButton.click();

        int filteredElementCount = shopElementSearcher.searchAllShopItems().size();

        assertTrue(filteredElementCount < startElementCount);
    }
}
