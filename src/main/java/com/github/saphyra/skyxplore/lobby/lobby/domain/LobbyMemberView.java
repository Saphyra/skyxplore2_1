package com.github.saphyra.skyxplore.lobby.lobby.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class LobbyMemberView {
    private final String characterId;
    private final String characterName;
    private final boolean isReady;
}
