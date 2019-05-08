package com.github.saphyra.selenium.logic.page;

import static com.github.saphyra.selenium.logic.util.WaitUtil.getWithWait;
import static com.github.saphyra.selenium.logic.util.WaitUtil.waitUntil;

import java.util.List;
import java.util.Optional;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.github.saphyra.selenium.logic.domain.Category;
import com.github.saphyra.selenium.logic.util.CategoryNameHelper;

@RequiredArgsConstructor
@Slf4j
public class FactoryPage {
    private static final String ELEMENT_PRODUCT_CONTAINER = ".content-element:first-child";
    private static final String ELEMENT_MATERIAL_CONTAINER = "#materials > div";
    private static final String ELEMENT_QUEUE = "#queue .queue-element";
    private static final String SELECTOR_CATEGORY_BUTTONS = ".menu-child div.button";

    private final WebDriver driver;
    private final CategoryNameHelper categoryNameHelper;

    public void loadItemsOfCategory(Category category) {
        List<WebElement> categoryButtons = getWithWait(() -> {
            List<WebElement> buttons = driver.findElements(By.cssSelector(SELECTOR_CATEGORY_BUTTONS));
            return buttons.isEmpty() ? Optional.empty() : Optional.of(buttons);
        }, "Querying category buttons...").orElseThrow(() -> new RuntimeException("Category buttons not found."));

        categoryButtons.stream()
            .filter(element -> element.getText().equals(categoryNameHelper.getCategoryName(category)))
            .findFirst()
            .orElseThrow(() -> new RuntimeException("Category button not found with categoryId " + category.getCategoryId()))
            .click();

        waitUntil(() -> !driver.findElements(By.cssSelector("#content")).isEmpty(), "Waiting for category list loading...");
    }

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
