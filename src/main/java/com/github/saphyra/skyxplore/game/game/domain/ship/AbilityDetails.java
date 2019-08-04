package com.github.saphyra.skyxplore.game.game.domain.ship;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
@Builder
public class AbilityDetails {
    @NonNull
    @Getter
    private final String itemId;

    @Getter
    private final int energyUsage;

    @Getter
    private final int reload;

    @Getter
    private final int active;

    @NonNull
    private final Map<String, Integer> data;

    public Map<String, Integer> getData() {
        return new HashMap<>(data);
    }
}
