package com.github.saphyra.selenium.test.community.helper;

import com.github.saphyra.selenium.logic.domain.BlockableCharacter;
import com.github.saphyra.selenium.logic.domain.BlockedCharacter;
import com.github.saphyra.selenium.logic.domain.SeleniumCharacter;
import com.github.saphyra.selenium.logic.domain.localization.Page;
import com.github.saphyra.selenium.logic.page.CommunityPage;
import com.github.saphyra.selenium.logic.validator.NotificationValidator;
import lombok.RequiredArgsConstructor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.stream.Collectors;

import static com.github.saphyra.selenium.logic.util.LocalizationUtil.getAdditionalContent;

@RequiredArgsConstructor
public class BlockTestHelper {
    private static final String MESSAGE_CODE_CHARACTER_BLOCKED = "character-blocked";

    private final WebDriver driver;
    private final CommunityPage communityPage;
    private final NotificationValidator notificationValidator;

    public void blockCharacter(SeleniumCharacter character) {
        searchCharacterCanBeBlocked(character.getCharacterName()).stream()
            .findFirst()
            .orElseThrow(() -> new RuntimeException("BlockableCharacter not found"))
            .block();

        notificationValidator.verifyNotificationVisibility(getAdditionalContent(driver, Page.COMMUNITY, MESSAGE_CODE_CHARACTER_BLOCKED));
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
            .map(element -> new BlockedCharacter(driver, element))
            .collect(Collectors.toList());
    }
}
