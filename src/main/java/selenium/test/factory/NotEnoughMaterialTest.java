package selenium.test.factory;

import org.junit.Test;
import org.openqa.selenium.WebElement;
import selenium.SeleniumTestApplication;
import selenium.logic.domain.SeleniumProduct;
import selenium.logic.flow.CreateCharacter;
import selenium.logic.flow.Navigate;
import selenium.logic.flow.Registration;
import selenium.logic.flow.SelectCharacter;
import selenium.logic.page.FactoryPage;
import selenium.test.factory.util.FactoryTestHelper;

import static org.junit.Assert.assertFalse;

public class NotEnoughMaterialTest extends SeleniumTestApplication {
    private FactoryTestHelper factoryTestHelper;
    private FactoryPage factoryPage;

    @Override
    protected void init() {
        factoryTestHelper = new FactoryTestHelper(
            new Registration(driver, messageCodes),
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
