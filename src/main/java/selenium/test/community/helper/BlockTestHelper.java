package selenium.test.community.helper;

import lombok.RequiredArgsConstructor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import selenium.logic.domain.BlockableCharacter;
import selenium.logic.domain.BlockedCharacter;
import selenium.logic.domain.SeleniumCharacter;
import selenium.logic.page.CommunityPage;
import selenium.logic.validator.NotificationValidator;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class BlockTestHelper {
    private static final String NOTIFICATION_CHARACTER_BLOCKED = "Karakter blokkolva.";

    private final WebDriver driver;
    private final CommunityPage communityPage;
    private final NotificationValidator notificationValidator;

    public void blockCharacter(SeleniumCharacter character) {
        searchForBlockableCharacters(character.getCharacterName()).stream()
            .findFirst()
            .orElseThrow(() -> new RuntimeException("BlockableCharacter not found"))
            .block();

        notificationValidator.verifyNotificationVisibility(NOTIFICATION_CHARACTER_BLOCKED);
    }

    public List<BlockableCharacter> searchForBlockableCharacters(String characterName) {
        if (!communityPage.getBlockableCharacterInputField().isDisplayed()) {
            openBlockCharacterWindow();
        }

        WebElement blockCharacterNameInputField = communityPage.getBlockCharacterNameInputField();
        blockCharacterNameInputField.clear();
        blockCharacterNameInputField.sendKeys(characterName);

        return communityPage.getBlockableCharacters().stream()
            .map(BlockableCharacter::new)
            .collect(Collectors.toList());
    }

    private void openBlockCharacterWindow() {
        communityPage.getBlockCharactersPageButton().click();
        communityPage.getBlockCharacterWindowButton().click();
    }

    public List<BlockedCharacter> getBlockedCharacters() {
         communityPage.getBlockCharactersPageButton().click();
        return communityPage.getBlockedCharacters().stream()
        .map(element -> new BlockedCharacter(driver, element))
        .collect(Collectors.toList());
    }
}
