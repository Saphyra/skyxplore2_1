package com.github.saphyra.selenium.logic.util;

import static com.github.saphyra.selenium.SeleniumTestApplication.ERROR_CODES;
import static com.github.saphyra.selenium.SeleniumTestApplication.PAGE_LOCALIZATIONS;
import static com.github.saphyra.selenium.logic.util.Util.executeScript;
import static com.github.saphyra.skyxplore.common.RequestConstants.DEFAULT_LOCALE;

import org.openqa.selenium.WebDriver;

import com.github.saphyra.selenium.logic.domain.localization.Page;
import com.github.saphyra.selenium.logic.domain.localization.PageLocalization;
import com.github.saphyra.selenium.logic.domain.localization.PageLocalizations;
import com.github.saphyra.selenium.logic.domain.localization.StringStringMap;
import com.github.saphyra.skyxplore.common.ErrorCode;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

@UtilityClass
@Slf4j
public class LocalizationUtil {
    public static String getErrorCode(WebDriver driver, ErrorCode errorCode) {
        log.info("Fetching errorCode for key {}", errorCode);
        String locale = getLocale(driver);
        try {
            return fetchErrorCode(locale, errorCode);
        } catch (Exception e) {
            log.info("ErrorCode not found for locale {}. Fetching from browserLanguage...", locale);
            String browserLanguage = getBrowserLanguage(driver);
            try {
                return fetchErrorCode(browserLanguage, errorCode);
            } catch (Exception e1) {
                log.info("ErrorCode not found for locale {}. Fetching from default language...", browserLanguage);
                return fetchErrorCode(DEFAULT_LOCALE, errorCode);
            }
        }
    }

    private static String fetchErrorCode(String locale, ErrorCode errorCode) {
        StringStringMap errorCodes = ERROR_CODES.get(locale);
        String code = errorCodes.get(errorCode.name());
        log.info("ErrorCode found for locale {} and key {}: {}", locale, errorCode, code);
        return code;
    }

    public static String getAdditionalContent(WebDriver driver, Page page, String key) {
        log.info("Fetching additionalContent for page {} and key {}", page, key);
        String locale = getLocale(driver);
        try {
            return fetchAdditionalContent(page, key, locale);
        } catch (Exception e) {
            log.info("Localization not found for page {} and locale {}. Fetching from browserLanguage...", page, locale);
            String browserLanguage = getBrowserLanguage(driver);
            try {
                return fetchAdditionalContent(page, key, locale);
            } catch (Exception e1) {
                log.info("Localization not found for page {} and locale {}. Fetching from default language...", page, browserLanguage);
                return fetchAdditionalContent(page, key, DEFAULT_LOCALE);
            }
        }
    }

    private static String fetchAdditionalContent(Page page, String key, String locale) {
        PageLocalizations pageLocalizations = PAGE_LOCALIZATIONS.get(locale);
        PageLocalization pageLocalization = pageLocalizations.get(page);
        String additionalContent = pageLocalization.getAdditionalContent(key);
        log.info("AdditionalContent read from page {} with key {}: {}", page, key, additionalContent);
        return additionalContent;
    }

    private static String getLocale(WebDriver driver) {
        String locale = executeScript(driver, "return getLocale()");
        log.info("Locale: {}", locale);
        return locale;
    }

    private static String getBrowserLanguage(WebDriver driver) {
        String locale = executeScript(driver, "return getBrowserLanguage()");
        log.info("BrowserLanguage: {}", locale);
        return locale;
    }
}
