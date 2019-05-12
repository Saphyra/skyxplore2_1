package com.github.saphyra.skyxplore.lobby.invitation.domain;

import com.github.saphyra.skyxplore.lobby.lobby.domain.GameMode;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class InvitationView {
    @NonNull
    private final GameMode gameMode;

    private final String data;

    @NonNull
    private final String characterName;

    @NonNull
    private final String characterId;

    @NonNull
    private final UUID lobbyId;
}
