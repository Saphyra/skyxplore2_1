package com.github.saphyra.selenium.logic.util;

import com.github.saphyra.selenium.logic.domain.Category;
import com.github.saphyra.selenium.logic.domain.localization.StringStringMap;
import com.github.saphyra.skyxplore.common.RequestConstants;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebDriver;

import java.io.IOException;
import java.net.URL;
import java.util.Map;
import java.util.Optional;

import static com.github.saphyra.selenium.SeleniumTestApplication.OBJECT_MAPPER;
import static java.util.Objects.isNull;

@Slf4j
public class CategoryNameHelper {
    private final Map<String, String> categories;

    public CategoryNameHelper(WebDriver driver) {
        String locale = LocalizationUtil.getLocale(driver);
        URL messageCodes = getClass().getClassLoader().getResource("public/i18n/" + locale + "/categories.json");

        if (isNull(messageCodes)) {
            String browserLanguage = LocalizationUtil.getBrowserLanguage(driver);
            log.info("Category Localization not found with locale {}. Using browserLanguage...", browserLanguage);
            messageCodes = getClass().getClassLoader().getResource("public/i18n/" + browserLanguage + "/categories.json");
        }

        if (isNull(messageCodes)) {
            log.info("Localization not found for locale {}. Using default locale...", RequestConstants.DEFAULT_LOCALE);
            messageCodes = getClass().getClassLoader().getResource("public/i18n/page/" + RequestConstants.DEFAULT_LOCALE + "/categories.json");
        }

        try {
            this.categories = OBJECT_MAPPER.readValue(messageCodes, StringStringMap.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String getCategoryName(Category category) {
        return Optional.ofNullable(categories.get(category.getCategoryId()))
            .orElseThrow(() -> new RuntimeException("No categoryName found with categoryId " + category));
    }
}
