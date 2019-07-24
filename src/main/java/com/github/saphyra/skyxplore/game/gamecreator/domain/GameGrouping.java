package com.github.saphyra.skyxplore.game.gamecreator.domain;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;
import java.util.Vector;

import com.github.saphyra.skyxplore.game.lobby.lobby.domain.GameMode;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

@Data
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
//TODO unit test
public class GameGrouping {
    @NonNull
    private final UUID gameGroupingId;

    @NonNull
    private final Integer expectedGameMembersAmount;

    @NonNull
    private final GameMode gameMode;

    private final Object data;

    @NonNull
    private final OffsetDateTime createdAt;

    @NonNull
    @Builder.Default
    private final List<GameGroup> gameGroups = new Vector<>();

    @Builder.Default
    @NonNull
    private final List<UUID> lockedLobbyIds = new Vector<>();

    public void addGroup(GameGroup group) {
        gameGroups.add(group);
    }

    public GameGrouping addGroups(List<GameGroup> gameGroups) {
        gameGroups.forEach(this::addGroup);
        return this;
    }

    public GameGrouping lockLobby(UUID lobbyId) {
        lockedLobbyIds.add(lobbyId);
        return this;
    }
}
