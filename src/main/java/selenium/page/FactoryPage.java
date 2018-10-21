package selenium.page;

import lombok.RequiredArgsConstructor;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

@RequiredArgsConstructor
public class FactoryPage {
    private static final String ELEMENT_PRODUCT_CONTAINER = ".contentelement:first-child";
    private final WebDriver driver;

    public WebElement getProductContainer() {
        return driver.findElement(By.cssSelector(ELEMENT_PRODUCT_CONTAINER));
    }
}
