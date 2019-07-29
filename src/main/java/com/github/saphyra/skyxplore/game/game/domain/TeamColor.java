package com.github.saphyra.skyxplore.game.game.domain;

import java.awt.Color;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum TeamColor {
    WHITE(new Color(255, 255, 255)),
    LIGHT_BLUE(new Color(0, 255, 255)),
    BLUE(new Color(0, 0, 255)),
    LIGHT_GREEN(new Color(127, 255, 0)),
    GREEN(new Color(0, 100, 0)),
    PINK(new Color(255, 105, 180)),
    PURPLE(new Color(156, 0, 139)),
    YELLOW(new Color(255, 255, 0)),
    ORANGE(new Color(255, 165, 0)),
    RED(new Color(255, 0, 0)),
    LIGHT_BROWN(new Color(222, 184, 135)),
    BROWN(new Color(165, 42, 42));

    private final Color colorCode;
}
