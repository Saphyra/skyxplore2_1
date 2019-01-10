package selenium.aanew.test.factory;

import org.junit.Test;
import org.openqa.selenium.WebElement;
import selenium.aanew.SeleniumTestApplication;
import selenium.aanew.logic.domain.SeleniumProduct;
import selenium.aanew.logic.flow.CreateCharacter;
import selenium.aanew.logic.flow.Navigate;
import selenium.aanew.logic.flow.Registration;
import selenium.aanew.logic.flow.SelectCharacter;
import selenium.aanew.logic.page.FactoryPage;
import selenium.aanew.test.factory.util.FactoryTestHelper;

import static org.junit.Assert.assertFalse;

public class NotEnoughMaterialTest extends SeleniumTestApplication {
    private FactoryTestHelper factoryTestHelper;
    private FactoryPage factoryPage;

    @Override
    protected void init() {
        factoryTestHelper = new FactoryTestHelper(
            new Registration(driver),
            new CreateCharacter(driver),
            new SelectCharacter(driver),
            new Navigate(driver)
        );
        factoryPage = new FactoryPage(driver);
    }

    @Test
    public void testNotEnoughMaterial() {
        factoryTestHelper.registerAndGoToFactoryPage();

        WebElement productElement = factoryPage.getProductContainer();
        SeleniumProduct product = new SeleniumProduct(productElement);

        product.fillAmountField(product.getTooExpensiveAmount());

        WebElement buildButton = product.getBuildButton();

        assertFalse(buildButton.isEnabled());
    }


}
