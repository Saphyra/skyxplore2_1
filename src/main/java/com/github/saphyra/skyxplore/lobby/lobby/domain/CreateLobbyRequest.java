package com.github.saphyra.skyxplore.lobby.lobby.domain;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class CreateLobbyRequest {
    @NotNull
    private GameMode gameMode;
    private String data;
}
