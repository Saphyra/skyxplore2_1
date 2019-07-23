package com.github.saphyra.skyxplore.game.lobby.lobby.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

@Data
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class LobbyMember {
    @NonNull
    private final String characterId;

    @Builder.Default
    private volatile boolean isReady = false;
}
