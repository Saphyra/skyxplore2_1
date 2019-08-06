package com.github.saphyra.skyxplore.game.lobby.lobby.domain;

import static java.util.Objects.isNull;

import java.util.Arrays;

public enum ClanWarsType {
    RANDOM, DEFENDER, DREADNOUGHT, ECLIPSE, SABOTEUR, STARTER, SUPPORTER;

    public static ClanWarsType fromValue(String value) {
        if (isNull(value)) {
            return null;
        }
        return Arrays.stream(values())
            .filter(teamfightType -> teamfightType.name().equalsIgnoreCase(value))
            .findFirst()
            .orElse(null);
    }
}
