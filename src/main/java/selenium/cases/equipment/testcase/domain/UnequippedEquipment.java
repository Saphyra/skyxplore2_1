package selenium.cases.equipment.testcase.domain;

import org.openqa.selenium.WebElement;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UnequippedEquipment {
    private final WebElement element;

    public String getId() {
        return element.getText().split(" ")[0];
    }

    public int getAmount() {
        return Integer.valueOf(element.getText().split("\\(x")[1].split("\\)")[0]);
    }
}
