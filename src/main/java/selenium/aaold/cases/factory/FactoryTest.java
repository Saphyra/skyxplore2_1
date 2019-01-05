package selenium.aaold.cases.factory;

import org.openqa.selenium.WebDriver;
import selenium.aanew.flow.Navigate;
import selenium.aanew.flow.Registration;
import selenium.aanew.validator.NotificationValidator;
import selenium.aaold.cases.factory.testcase.BuildAndFinishTest;
import selenium.aaold.cases.factory.testcase.NotEnoughMaterialTest;
import selenium.aanew.domain.SeleniumCharacter;
import selenium.aanew.flow.CreateCharacter;
import selenium.aanew.flow.SelectCharacter;
import selenium.aanew.page.FactoryPage;

public class FactoryTest {

    private final WebDriver driver;
    private final FactoryPage factoryPage;
    private final Navigate navigate;
    private final NotificationValidator notificationValidator;
    private final SeleniumCharacter testCharacter = SeleniumCharacter.create();

    private FactoryTest(WebDriver driver){
        this.driver = driver;
        this.factoryPage = new FactoryPage(driver);
        this.navigate = new Navigate(driver);
        this.notificationValidator = new NotificationValidator(driver);
    }

    public static void run(WebDriver driver){
        FactoryTest testCase = new FactoryTest(driver);
        testCase.init();
        testCase.testNotEnoughMaterials();

        driver.navigate().refresh();

        testCase.testBuildAndFinish();
    }

    private void testNotEnoughMaterials() {
        NotEnoughMaterialTest test = NotEnoughMaterialTest.builder()
            .driver(driver)
            .factoryPage(factoryPage)
            .build();

        test.testNotEnoughMaterial();
    }

    private void testBuildAndFinish() {
        BuildAndFinishTest test = BuildAndFinishTest.builder()
            .driver(driver)
            .factoryPage(factoryPage)
            .notificationValidator(notificationValidator)
            .build();

        test.testBuildAndFinish();
    }

    private void init() {
        new Registration(driver).registerUser();
        new CreateCharacter(driver).createCharacter(testCharacter);
        new SelectCharacter(driver).selectCharacter(testCharacter);
        navigate.toFactory();
    }
}
