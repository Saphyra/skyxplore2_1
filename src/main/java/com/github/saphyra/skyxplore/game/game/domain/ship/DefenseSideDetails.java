package com.github.saphyra.skyxplore.game.game.domain.ship;

import java.util.List;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
@Builder
public class DefenseSideDetails {
    private final List<HullDetails> hulls;
    private final List<ShieldDetails> shields;
}
