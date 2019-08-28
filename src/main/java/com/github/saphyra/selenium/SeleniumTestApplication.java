package com.github.saphyra.selenium;

import static com.github.saphyra.selenium.logic.util.LinkUtil.HOST;
import static com.github.saphyra.selenium.logic.util.LinkUtil.HOST_TEST;
import static com.github.saphyra.selenium.logic.util.Util.executeScript;
import static com.github.saphyra.selenium.logic.util.WaitUtil.sleep;
import static com.github.saphyra.skyxplore.Application.APP_CTX;

import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.boot.SpringApplication;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.saphyra.skyxplore.Application;
import lombok.extern.slf4j.Slf4j;

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
    protected WebDriver driver;
    protected String locale;

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

        getLocale();

        driver.manage().window().maximize();
        driver.get(HOST);

        init();
    }

    private void getLocale() {
        locale = executeScript(driver, "return navigator.language.toLowerCase().split(\"-\")[0]");
        log.info("Locale: {}", locale);
    }

    protected abstract void init();

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
