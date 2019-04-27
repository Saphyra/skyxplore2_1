package selenium.test.community.helper;

import lombok.RequiredArgsConstructor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import selenium.logic.domain.BlockableCharacter;
import selenium.logic.domain.BlockedCharacter;
import selenium.logic.domain.MessageCodes;
import selenium.logic.domain.SeleniumCharacter;
import selenium.logic.page.CommunityPage;
import selenium.logic.validator.NotificationValidator;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class BlockTestHelper {
    private static final String MESSAGE_CODE_CHARACTER_BLOCKED = "CHARACTER_BLOCKED";

    private final WebDriver driver;
    private final CommunityPage communityPage;
    private final NotificationValidator notificationValidator;
    private final MessageCodes messageCodes;

    public void blockCharacter(SeleniumCharacter character) {
        searchCharacterCanBeBlocked(character.getCharacterName()).stream()
            .findFirst()
            .orElseThrow(() -> new RuntimeException("BlockableCharacter not found"))
            .block();

        notificationValidator.verifyNotificationVisibility(messageCodes.get(MESSAGE_CODE_CHARACTER_BLOCKED));
    }

    public List<BlockableCharacter> searchCharacterCanBeBlocked(String characterName) {
        if (!communityPage.getBlockCharacterWindow().isDisplayed()) {
            openBlockCharacterWindow();
        }

        WebElement blockCharacterNameInputField = communityPage.getBlockCharacterNameInputField();
        blockCharacterNameInputField.clear();
        blockCharacterNameInputField.sendKeys(characterName);

        return communityPage.getCharactersCanBeBlocked().stream()
            .map(element -> new BlockableCharacter(element, driver))
            .collect(Collectors.toList());
    }

    private void openBlockCharacterWindow() {
        WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
        communityPage.getBlockCharactersPageButton().click();
        webDriverWait.until(ExpectedConditions.visibilityOf(communityPage.getBlockedCharactersContainer()));
        communityPage.getBlockCharacterWindowButton().click();
        webDriverWait.until(ExpectedConditions.visibilityOf(communityPage.getBlockCharacterWindow()));
    }

    public List<BlockedCharacter> getBlockedCharacters() {
        return communityPage.getBlockedCharacters().stream()
            .map(element -> new BlockedCharacter(element, messageCodes))
            .collect(Collectors.toList());
    }
}
