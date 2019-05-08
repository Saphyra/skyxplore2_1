package com.github.saphyra.selenium.logic.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Category {
    ENERGY("energy"), EXTENDER("extender"), STARTER_SHIP("starter"), MATERIAL("material");

    @Getter
    private final String categoryId;
}
