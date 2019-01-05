package selenium.aanew.logic.page;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

@RequiredArgsConstructor
@Slf4j
public class FactoryPage {
    private static final String ELEMENT_PRODUCT_CONTAINER = ".contentelement:first-child";
    private static final String ELEMENT_MATERIAL_CONTAINER = "#materials > div";
    private static final String ELEMENT_QUEUE = "#queue > div";

    private final WebDriver driver;

    public WebElement getProductContainer() {
        return driver.findElement(By.cssSelector(ELEMENT_PRODUCT_CONTAINER));
    }

    public List<WebElement> getCurrentMaterialAmounts() {
        return driver.findElements(By.cssSelector(ELEMENT_MATERIAL_CONTAINER));
    }

    public List<WebElement> getQueue() {
        return driver.findElements(By.cssSelector(ELEMENT_QUEUE));
    }
}
