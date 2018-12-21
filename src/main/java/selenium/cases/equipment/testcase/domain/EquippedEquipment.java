package selenium.cases.equipment.testcase.domain;

import org.openqa.selenium.WebElement;

import lombok.Getter;

public class EquippedEquipment {
    @Getter
    private final WebElement element;
    private final String text;

    public EquippedEquipment(WebElement element) {
        this.element = element;
        this.text = element.getText();
    }

    public void unequip() {
        element.click();
    }

    public String getId() {
        return text.split(" ")[0];
    }
}
