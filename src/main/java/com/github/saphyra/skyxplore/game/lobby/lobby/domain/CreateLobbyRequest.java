package com.github.saphyra.skyxplore.game.lobby.lobby.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateLobbyRequest {
    @NotNull
    private GameMode gameMode;
    private String data;
}
