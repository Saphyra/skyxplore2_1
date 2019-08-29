package com.github.saphyra.selenium;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.saphyra.selenium.logic.domain.localization.ErrorCodes;
import com.github.saphyra.selenium.logic.domain.localization.PageLocalizations;
import com.github.saphyra.selenium.logic.util.PageLocalizationLoader;
import com.github.saphyra.skyxplore.Application;
import lombok.extern.slf4j.Slf4j;
import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.boot.SpringApplication;

import java.util.List;
import java.util.Map;

import static com.github.saphyra.selenium.logic.util.ErrorCodeLoader.loadErrorCodes;
import static com.github.saphyra.selenium.logic.util.LinkUtil.HOST;
import static com.github.saphyra.selenium.logic.util.LinkUtil.HOST_TEST;
import static com.github.saphyra.selenium.logic.util.WaitUtil.sleep;
import static com.github.saphyra.skyxplore.Application.APP_CTX;

@Slf4j
public abstract class SeleniumTestApplication {
    private static final String ARG_PROFILE = "--spring.profiles.active=test";
    private static final String[] ARGS = new String[]{
        ARG_PROFILE
    };
    private static final String CHROME_DRIVER_PROPERTY_NAME = "webdriver.chrome.driver";
    private static final String CHROME_DRIVER_EXE_LOCATION = "chromedriver.exe";
    private static final boolean HEADLESS_MODE = true;

    public static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    public static final Map<String, PageLocalizations> PAGE_LOCALIZATIONS = PageLocalizationLoader.loadPageLocalizations();
    public static final ErrorCodes ERROR_CODES = loadErrorCodes();

    protected WebDriver driver;


    @Before
    public void startServices() {
        if (HOST.equals(HOST_TEST)) {
            Application.main(ARGS);
        }

        System.setProperty(CHROME_DRIVER_PROPERTY_NAME, getClass().getClassLoader().getResource(CHROME_DRIVER_EXE_LOCATION).getPath());
        ChromeOptions options = new ChromeOptions();
        options.setHeadless(HEADLESS_MODE);
        options.addArguments("window-size=1920,1080");

        driver = new ChromeDriver(options);

        driver.manage().window().maximize();
        driver.get(HOST);

        init();
    }

    protected abstract void init();

    @After
    public void tearDown() {
        List<WebElement> webElements = driver.findElements(By.id("logcontainermain"));
        if (!webElements.isEmpty()) {
            log.info(webElements.get(0).getText());
        }

        sleep(2000);
        if (APP_CTX != null) {
            SpringApplication.exit(APP_CTX);
        }
        driver.close();
        driver.quit();
    }
}
