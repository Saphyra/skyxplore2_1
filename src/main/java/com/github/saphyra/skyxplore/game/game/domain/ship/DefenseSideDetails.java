package com.github.saphyra.skyxplore.game.game.domain.ship;

import java.util.List;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
@Builder
@Getter
public class DefenseSideDetails {
    @NonNull
    private final List<HullDetails> hulls;

    @NonNull
    private final List<ShieldDetails> shields;
}
