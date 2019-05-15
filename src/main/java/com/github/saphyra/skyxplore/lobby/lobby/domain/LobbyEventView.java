package com.github.saphyra.skyxplore.lobby.lobby.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class LobbyEventView {
    private final LobbyEventType eventType;
    private final Object data;
}
