package com.github.saphyra.skyxplore.lobby.domain;

import static java.util.Objects.isNull;

import java.util.Arrays;

public enum GameMode {
    ARCADE, BATTLE_ROYALE, CLAN_WARS, TEAMFIGHT, VS, TOURNAMENT;

    public GameMode fromValue(String value) {
        if (isNull(value)) {
            return null;
        }
        return Arrays.stream(values())
            .filter(gameMode -> gameMode.name().equalsIgnoreCase(value))
            .findFirst()
            .orElse(null);
    }
}
