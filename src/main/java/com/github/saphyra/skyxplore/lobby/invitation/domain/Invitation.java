package com.github.saphyra.skyxplore.lobby.invitation.domain;


import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

import java.time.OffsetDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class Invitation {
    @NonNull
    private final UUID invitationId;
    @NonNull
    private final String characterId;
    @NonNull
    private final String invitedCharacterId;
    @NonNull
    private final UUID lobbyId;
    @NonNull
    private final OffsetDateTime createdAt;

    @Builder.Default
    private boolean queried = false;
}
