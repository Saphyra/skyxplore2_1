package selenium.aanew.domain;

import org.openqa.selenium.WebElement;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@RequiredArgsConstructor
@ToString
public class UnequippedEquipment {
    @Getter
    private final WebElement element;

    public String getId() {
        return element.getText().split(" ")[0];
    }

    public int getAmount() {
        return Integer.valueOf(element.getText().split("\\(x")[1].split("\\)")[0]);
    }
}
