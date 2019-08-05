package com.github.saphyra.skyxplore.game.game.domain.ship;


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
public class DefenseDetails {
    @NonNull
    private final DefenseSideDetails frontDefense;

    @NonNull
    private final DefenseSideDetails leftDefense;

    @NonNull
    private final DefenseSideDetails rightDefense;

    @NonNull
    private final DefenseSideDetails backDefense;
}
