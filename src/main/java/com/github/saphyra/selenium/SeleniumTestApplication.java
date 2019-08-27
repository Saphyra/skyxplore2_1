package com.github.saphyra.selenium;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.saphyra.selenium.logic.domain.localization.MessageCodes;
import com.github.saphyra.selenium.logic.domain.localization.PageLocalization;
import com.github.saphyra.skyxplore.Application;
import lombok.extern.slf4j.Slf4j;
import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.boot.SpringApplication;

import java.io.IOException;
import java.net.URL;

import static com.github.saphyra.selenium.logic.util.LinkUtil.HOST;
import static com.github.saphyra.selenium.logic.util.LinkUtil.HOST_TEST;
import static com.github.saphyra.selenium.logic.util.Util.executeScript;
import static com.github.saphyra.selenium.logic.util.WaitUtil.sleep;
import static com.github.saphyra.skyxplore.Application.APP_CTX;
import static java.util.Objects.isNull;

@Slf4j
public abstract class SeleniumTestApplication {
    private static final String ARG_PROFILE = "--spring.profiles.active=test";
    private static final String[] ARGS = new String[]{
        ARG_PROFILE
    };
    private static final String CHROME_DRIVER_PROPERTY_NAME = "webdriver.chrome.driver";
    private static final String CHROME_DRIVER_EXE_LOCATION = "chromedriver.exe";
    private static final boolean HEADLESS_MODE = true;

    protected static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    protected WebDriver driver;
    protected String locale;
    protected MessageCodes messageCodes;

    @Before
    public void startServices() throws IOException {
        if (HOST.equals(HOST_TEST)) {
            Application.main(ARGS);
        }

        System.setProperty(CHROME_DRIVER_PROPERTY_NAME, getClass().getClassLoader().getResource(CHROME_DRIVER_EXE_LOCATION).getPath());
        ChromeOptions options = new ChromeOptions();
        options.setHeadless(HEADLESS_MODE);
        options.addArguments("window-size=1920,1080");

        driver = new ChromeDriver(options);

        getLocale();
        readMessageCodes();

        driver.manage().window().maximize();
        driver.get(HOST);

        init();
    }

    private void getLocale() {
        locale = executeScript(driver, "return navigator.language.toLowerCase().split(\"-\")[0]");
        log.info("Locale: {}", locale);
    }

    private void readMessageCodes() throws IOException {
        URL messageCodes = getClass().getClassLoader().getResource("public/i18n/" + locale + "/message_codes.json");

        if (isNull(messageCodes)) {
            log.info("Localization not found for locale {}. Using default locale...", locale);
            messageCodes = getClass().getClassLoader().getResource("public/i18n/page/hu/message_codes.json");
        }

        this.messageCodes = OBJECT_MAPPER.readValue(messageCodes, MessageCodes.class);
    }

    protected abstract void init();

    protected PageLocalization getPageLocalization(String pageName) {
        URL source = getClass().getClassLoader().getResource("public/i18n/" + locale + "/" + pageName + ".json");

        if (isNull(source)) {
            log.info("Localization not found for locale {}. Using default locale...", locale);
            source = getClass().getClassLoader().getResource("public/i18n/page/hu/" + pageName + ".json");
        }

        if(isNull(source)){
            throw new RuntimeException("PageLocalization not found for page " + pageName);
        }

        try {
            return OBJECT_MAPPER.readValue(source, PageLocalization.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @After
    public void tearDown() {
        sleep(2000);
        if (APP_CTX != null) {
            SpringApplication.exit(APP_CTX);
        }
        driver.close();
        driver.quit();
    }
}
