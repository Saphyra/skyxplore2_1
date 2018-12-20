package selenium.cases.shop;

import org.openqa.selenium.WebDriver;
import selenium.cases.shop.testcase.BuyTest;
import selenium.cases.shop.testcase.CartTest;
import selenium.cases.shop.testcase.FilterTest;
import selenium.domain.SeleniumCharacter;
import selenium.flow.CreateCharacter;
import selenium.flow.Navigate;
import selenium.flow.Registration;
import selenium.flow.SelectCharacter;

public class ShopTest {
    private final WebDriver driver;
    private final Navigate navigate;

    private ShopTest(WebDriver driver) {
        this.driver = driver;
        navigate = new Navigate(driver);
    }

    public static void run(WebDriver driver) {
        ShopTest testCase = new ShopTest(driver);
        testCase.init();
        testCase.runFilterTests();
        testCase.runCartTests();
        testCase.runBuyTests();
    }

    private void runFilterTests() {
        FilterTest filterTest = new FilterTest(driver);
        filterTest.testFilters();
    }

    private void runCartTests() {
        CartTest cartTest = new CartTest(driver);
        cartTest.init();
        cartTest.testAddToCart();
        cartTest.testRemoveFromCart();
        cartTest.testCannotAddMoreWhenTooExpensive();
    }

    private void runBuyTests() {
        BuyTest buyTest = new BuyTest(driver, navigate);
        buyTest.init();
        buyTest.testBuyEquipment();
    }

    private void init() {
        new Registration(driver).registerUser();
        SeleniumCharacter testCharacter = SeleniumCharacter.create();
        new CreateCharacter(driver).createCharacter(testCharacter);
        new SelectCharacter(driver).selectCharacter(testCharacter);
        navigate.toShop();
    }
}
