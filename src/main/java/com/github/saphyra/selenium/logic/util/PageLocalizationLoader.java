package com.github.saphyra.selenium.logic.util;

import static com.github.saphyra.selenium.SeleniumTestApplication.OBJECT_MAPPER;
import static java.util.Objects.isNull;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import com.github.saphyra.selenium.logic.domain.localization.Page;
import com.github.saphyra.selenium.logic.domain.localization.PageLocalization;
import com.github.saphyra.selenium.logic.domain.localization.PageLocalizations;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PageLocalizationLoader {
    private static final String BASE_PATH = "src/main/resources/";
    private static final String RESOURCE_PATH = "public/i18n/page/";
    private static final File BASE_DIR = new File(BASE_PATH + RESOURCE_PATH);

    public static Map<String, PageLocalizations> loadPageLocalizations() {
        File[] localizationDirs = BASE_DIR.listFiles(File::isDirectory);
        log.info("localizationDirs: " + Arrays.toString(localizationDirs));
        if (isNull(localizationDirs)) {
            throw new RuntimeException("No page localization was found.");
        }

        Map<String, PageLocalizations> result = new HashMap<>();
        for (File localizationDir : localizationDirs) {
            log.info("Loading pageLocalization from localizationDit {}", localizationDir.getName());
            String locale = localizationDir.getName();
            result.put(locale, getPageLocalization(locale));
        }
        return result;
    }

    private static PageLocalizations getPageLocalization(String locale) {
        Map<Page, PageLocalization> pagePageLocalizationMap = new HashMap<>();
        for (Page page : Page.values()) {
            log.info("Loading pageLocalization for page {} and locale {}", page, locale);
            pagePageLocalizationMap.put(page, getPageLocalization(page.getPageName(), locale));
        }
        return new PageLocalizations(pagePageLocalizationMap);
    }

    private static PageLocalization getPageLocalization(String pageName, String locale) {
        URL source = PageLocalizationLoader.class.getClassLoader().getResource(RESOURCE_PATH + locale + "/" + pageName + ".json");

        if (isNull(source)) {
            throw new RuntimeException("PageLocalization not found for page " + pageName + " and locale " + locale);
        }

        try {
            return OBJECT_MAPPER.readValue(source, PageLocalization.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
