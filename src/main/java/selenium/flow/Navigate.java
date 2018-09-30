package selenium.flow;

import org.openqa.selenium.WebDriver;
import selenium.page.CharacterSelectPage;

import static org.junit.Assert.assertEquals;
import static selenium.util.LinkUtil.ACCOUNT;

public class Navigate {
    private final WebDriver driver;

    public Navigate(WebDriver driver) {
        this.driver = driver;
    }

    public void toAccountPage(){
        new CharacterSelectPage(driver).getAccountPageButton().click();
        assertEquals(ACCOUNT, driver.getCurrentUrl());
    }
}
