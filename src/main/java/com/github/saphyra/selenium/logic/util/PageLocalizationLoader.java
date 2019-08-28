package com.github.saphyra.selenium.logic.util;

import static com.github.saphyra.selenium.SeleniumTestApplication.OBJECT_MAPPER;
import static java.util.Objects.isNull;

import java.io.IOException;
import java.net.URL;

import com.github.saphyra.selenium.logic.domain.localization.PageLocalization;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PageLocalizationLoader {
    public PageLocalization getPageLocalization(String pageName, String locale) {
        URL source = getClass().getClassLoader().getResource("public/i18n/" + locale + "/" + pageName + ".json");

        if (isNull(source)) {
            log.info("Localization not found for locale {}. Using default locale...", locale);
            source = getClass().getClassLoader().getResource("public/i18n/page/hu/" + pageName + ".json");
        }

        if (isNull(source)) {
            throw new RuntimeException("PageLocalization not found for page " + pageName);
        }

        try {
            return OBJECT_MAPPER.readValue(source, PageLocalization.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
