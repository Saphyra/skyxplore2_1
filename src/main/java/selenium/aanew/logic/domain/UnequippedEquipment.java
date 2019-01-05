package selenium.aanew.logic.domain;

import org.openqa.selenium.WebElement;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@RequiredArgsConstructor
@ToString
public class UnequippedEquipment {
    private static final String REGEX_ID = " ";
    private static final String REGEX_AMOUNT_1 = "\\(x";
    private static final String REGEX_AMOUNT_2 = "\\)";
    @Getter
    private final WebElement element;

    public String getId() {
        return element.getText().split(REGEX_ID)[0];
    }

    public int getAmount() {
        return Integer.valueOf(element.getText().split(REGEX_AMOUNT_1)[1].split(REGEX_AMOUNT_2)[0]);
    }
}
