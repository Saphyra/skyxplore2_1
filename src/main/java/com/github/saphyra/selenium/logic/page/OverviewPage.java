package com.github.saphyra.selenium.logic.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class OverviewPage {
    private static final String ELEMENT_EQUIPMENT_PAGE_BUTTON = ".menu-container a:nth-child(1)";
    private static final String ELEMENT_FACTORY_PAGE_BUTTON = ".menu-container a:nth-child(2)";
    private static final String ELEMENT_SHOP_PAGE_BUTTON = ".menu-container a:nth-child(3)";
    private static final String SELECTOR_NOTIFICATION_NUMBER = "notification-num";

    private final WebDriver driver;

    public WebElement getEquipmentButton() {
        return driver.findElement(By.cssSelector(ELEMENT_EQUIPMENT_PAGE_BUTTON));
    }

    public WebElement getFactoryButton() {
        return driver.findElement(By.cssSelector(ELEMENT_FACTORY_PAGE_BUTTON));
    }

    public WebElement getShopButton() {
        return driver.findElement(By.cssSelector(ELEMENT_SHOP_PAGE_BUTTON));
    }

    public WebElement getNotificationNumberElement() {
        return driver.findElement(By.id(SELECTOR_NOTIFICATION_NUMBER));
    }
}
