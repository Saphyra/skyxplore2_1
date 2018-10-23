package selenium.cases.shop;

import org.openqa.selenium.WebDriver;
import selenium.domain.SeleniumCharacter;
import selenium.flow.CreateCharacter;
import selenium.flow.Navigate;
import selenium.flow.Registration;
import selenium.flow.SelectCharacter;

public class ShopTest {
    private final WebDriver driver;
    private final Navigate navigate;

    private ShopTest(WebDriver driver){
        this.driver = driver;
        navigate = new Navigate(driver);
    }

    public static void run(WebDriver driver){
        /*
        Add to cart
        Remove from cart
        Cannot add too expensive
        Buy all
        Exists in equipment
         */

        ShopTest testCase = new ShopTest(driver);
        testCase.init();
    }

    private void init() {
        new Registration(driver).registerUser();
        SeleniumCharacter testCharacter = SeleniumCharacter.create();
        new CreateCharacter(driver).createCharacter(testCharacter);
        new SelectCharacter(driver).selectCharacter(testCharacter);
        navigate.toShop();
    }
}
