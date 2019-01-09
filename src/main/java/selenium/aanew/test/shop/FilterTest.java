package selenium.aanew.test.shop;

import org.junit.Test;
import org.openqa.selenium.WebElement;
import selenium.aanew.SeleniumTestApplication;
import selenium.aanew.logic.flow.CreateCharacter;
import selenium.aanew.logic.flow.Navigate;
import selenium.aanew.logic.flow.Registration;
import selenium.aanew.logic.flow.SelectCharacter;
import selenium.aanew.logic.helper.ShopElementSearcher;
import selenium.aanew.test.shop.util.ShopTestInitializer;

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
