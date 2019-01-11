package selenium.logic.domain;

import org.openqa.selenium.WebElement;

public class EquippedEquipment {
    private static final String REGEX_GET_ID = " ";
    private static final String ATTRIBUTE_CLASS = "class";
    private static final String VALUE_EMPTY_SLOT = "emptyslot";

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
        return text.split(REGEX_GET_ID)[0];
    }

    public boolean isEmpty() {
        return element.getAttribute(ATTRIBUTE_CLASS).contains(VALUE_EMPTY_SLOT);
    }
}
