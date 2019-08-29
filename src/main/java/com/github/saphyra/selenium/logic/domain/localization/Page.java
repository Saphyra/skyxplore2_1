package com.github.saphyra.selenium.logic.domain.localization;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Page {
    ACCOUNT("account"),
    CHARACTER_SELECT("characterselect"),
    COMMUNITY("community"),
    INDEX("index");

    @Getter
    private final String pageName;
}
