package com.github.saphyra.skyxplore.game.gamecreator.domain;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.Vector;

import com.github.saphyra.skyxplore.game.lobby.lobby.domain.GameMode;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@ToString
@EqualsAndHashCode
public class GameGrouping {
    @NonNull
    @Getter
    private final UUID gameGroupingId;

    @NonNull
    @Getter
    private final Integer minimumGameMembersAmount;

    @NonNull
    @Getter
    private final GameGroupSizeRange gameGroupSizeRange;

    @NonNull
    @Getter
    private final GameMode gameMode;

    @Getter
    private final Object data;

    @NonNull
    @Getter
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

    public boolean hasEnoughMembers() {
        return getMembersAmount() >= minimumGameMembersAmount;
    }

    public int getMembersAmount() {
        return Math.toIntExact(gameGroups.stream()
            .map(gameGroup -> gameGroup.getCharacters().size())
            .count());
    }

    public List<GameGroup> getGameGroups() {
        return new ArrayList<>(gameGroups);
    }

    public List<UUID> getLockedLobbyIds() {
        return new ArrayList<>(lockedLobbyIds);
    }
}
