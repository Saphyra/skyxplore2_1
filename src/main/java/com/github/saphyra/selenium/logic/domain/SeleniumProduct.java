package com.github.saphyra.selenium.logic.domain;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Slf4j
public class SeleniumProduct {
    private static final String ELEMENT_AMOUNT_FIELD = "label input";
    private static final String ELEMENT_MATERIAL_COST = "div:nth-child(2) div:first-child";
    private static final String ELEMENT_BUILD_BUTTON = "button";
    private static final String ELEMENT_MATERIAL_FIELDS = "div:nth-child(2) div";
    private static final String SELECTOR_NAME = ".content-element-title";

    private final WebElement webElement;

    public void fillAmountField(int amount) {
        WebElement amountField = getAmountField();
        amountField.clear();
        amountField.sendKeys(String.valueOf(amount));
    }

    private WebElement getAmountField() {
        return webElement.findElement(By.cssSelector(ELEMENT_AMOUNT_FIELD));
    }

    public int getTooExpensiveAmount() {
        String materialCost = webElement.findElement(By.cssSelector(ELEMENT_MATERIAL_COST)).getText();
        String materialCosts = materialCost.split(": ")[1];
        int cost = Integer.valueOf(materialCosts.split(" / ")[0]);
        int current = Integer.valueOf(materialCosts.split(" / ")[1]);

        return current / cost + 1;
    }

    public WebElement getBuildButton() {
        return webElement.findElement(By.cssSelector(ELEMENT_BUILD_BUTTON));
    }

    public Map<String, Integer> getUserMaterialAmounts() {
        return webElement.findElements(By.cssSelector(ELEMENT_MATERIAL_FIELDS)).stream()
            .collect(Collectors.toMap(this::parseMaterialName, this::parseMaterialAmount));
    }

    private String parseMaterialName(WebElement element) {
        return element.getText().split(": ")[0];
    }

    private Integer parseMaterialAmount(WebElement element) {
        return Integer.valueOf(element.getText().split(": ")[1].split(" / ")[0]);
    }

    public String getName() {
        return webElement.findElement(By.cssSelector(SELECTOR_NAME)).getText();
    }
}
