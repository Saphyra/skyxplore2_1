package com.github.saphyra.skyxplore.lobby.lobby.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

@Builder
@Data
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class LobbyEventView {
    @NonNull
    private final LobbyEventType eventType;
    private final Object data;
}
