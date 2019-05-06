package org.github.saphyra.skyxplore.lobby.domain;

import lombok.Data;

@Data
public class CreateLobbyRequest {
    private String gameMode;
    private String data;
}
