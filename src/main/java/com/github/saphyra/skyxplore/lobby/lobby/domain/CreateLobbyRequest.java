package com.github.saphyra.skyxplore.lobby.lobby.domain;

import lombok.Data;

@Data
public class CreateLobbyRequest {
    private GameMode gameMode;
    private String data;
}
