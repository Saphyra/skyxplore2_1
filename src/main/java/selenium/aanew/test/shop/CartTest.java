package selenium.aanew.test.shop;

import org.junit.Test;
import selenium.aanew.SeleniumTestApplication;
import selenium.aanew.logic.flow.CreateCharacter;
import selenium.aanew.logic.flow.Navigate;
import selenium.aanew.logic.flow.Registration;
import selenium.aanew.logic.flow.SelectCharacter;
import selenium.aanew.logic.helper.CostCounter;
import selenium.aanew.logic.helper.ShopElementSearcher;
import selenium.aanew.logic.validator.CartVerifier;
import selenium.aanew.test.shop.cart.AddToCartTest;
import selenium.aanew.test.shop.cart.RemoveFromCartTest;
import selenium.aanew.test.shop.cart.TooExpensiveTest;
import selenium.aanew.test.shop.util.ShopTestHelper;
import selenium.aanew.test.shop.util.ShopTestInitializer;

public class CartTest extends SeleniumTestApplication {
    private CartVerifier cartVerifier;
    private ShopTestHelper shopTestHelper;
    private ShopTestInitializer shopTestInitializer;
    private ShopElementSearcher shopElementSearcher;
    private CostCounter costCounter;

    @Override
    protected void init() {
        shopElementSearcher = new ShopElementSearcher(driver);
        costCounter = new CostCounter(driver, shopElementSearcher);
        cartVerifier = new CartVerifier(driver, shopElementSearcher, costCounter);
        shopTestHelper = new ShopTestHelper(shopElementSearcher);
        shopTestInitializer = new ShopTestInitializer(
            new Registration(driver),
            new CreateCharacter(driver),
            new SelectCharacter(driver),
            new Navigate(driver)
        );
    }

    @Test
    public void testAddToCart() {
        AddToCartTest.builder()
            .cartVerifier(cartVerifier)
            .shopTestHelper(shopTestHelper)
            .shopTestInitializer(shopTestInitializer)
            .build()
            .testAddToCart();
    }

    @Test
    public void testRemoveFromCart() {
        RemoveFromCartTest.builder()
            .cartVerifier(cartVerifier)
            .shopTestHelper(shopTestHelper)
            .shopTestInitializer(shopTestInitializer)
            .build()
            .testRemoveFromCart();
    }

    @Test
    public void testTooExpensiveItem() {
        TooExpensiveTest.builder()
            .shopTestInitializer(shopTestInitializer)
            .shopElementSearcher(shopElementSearcher)
            .shopTestHelper(shopTestHelper)
            .costCounter(costCounter)
            .build()
            .testTooExpensiveItem();
    }
}
