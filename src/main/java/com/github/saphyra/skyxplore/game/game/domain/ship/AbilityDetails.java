package com.github.saphyra.skyxplore.game.game.domain.ship;

import java.util.Map;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
@Builder
public class AbilityDetails {
    @NonNull
    private final String itemId;
    private final int energyUsage;
    private final int reload;
    private final int active;

    @NonNull
    private final Map<String, Integer> data;
}
