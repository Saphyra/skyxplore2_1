package selenium.cases.factory.testcase;

import lombok.Builder;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import selenium.page.FactoryPage;

import static org.junit.Assert.assertFalse;

@Builder
public class NotEnoughMaterialTest {
    private final WebDriver driver;
    private final FactoryPage factoryPage;

    public void testNotEnoughMaterial() {
        WebElement productElement = factoryPage.getProductContainer();
        SeleniumProduct product = new SeleniumProduct(productElement);

        product.fillAmountField(product.getTooExpensiveAmount());

        WebElement buildButton = product.getBuildButton();

        assertFalse(buildButton.isEnabled());
    }
}
