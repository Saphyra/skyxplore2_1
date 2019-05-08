package com.github.saphyra.skyxplore.lobby.domain;

import java.util.UUID;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Data
@Builder
public class Lobby {
    @NonNull
    private final UUID lobbyId;

    @NonNull
    private final GameMode gameMode;

    @NonNull
    private final FixedSizeConcurrentList<String> users;

    private final String data;

    @NonNull
    private volatile String ownerId;
}
