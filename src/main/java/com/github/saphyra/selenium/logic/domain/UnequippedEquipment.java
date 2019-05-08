package com.github.saphyra.selenium.logic.domain;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@ToString
@Slf4j
public class UnequippedEquipment {
    private static final String REGEX_ID = " ";
    private static final String SELECTOR_ITEM_NAME = "span:first-child";
    private static final String SELECTOR_AMOUNT = "span:nth-child(3)";
    @Getter
    private final WebElement element;

    public String getId() {
        String itemName = element.findElement(By.cssSelector(SELECTOR_ITEM_NAME)).getText();
        log.debug("UnequippedEquipment itemName: {}", itemName);
        return itemName.split(REGEX_ID)[0];
    }

    public int getAmount() {
        return Integer.valueOf(element.findElement(By.cssSelector(SELECTOR_AMOUNT)).getText());
    }
}
