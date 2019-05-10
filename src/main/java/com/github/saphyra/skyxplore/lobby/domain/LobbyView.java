package com.github.saphyra.skyxplore.lobby.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Data
@Builder
public class LobbyView {
    @NonNull
    private GameMode gameMode;
    private String data;
}