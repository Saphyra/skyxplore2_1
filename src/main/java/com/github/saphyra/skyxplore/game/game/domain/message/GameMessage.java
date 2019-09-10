package com.github.saphyra.skyxplore.game.game.domain.message;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

import java.util.List;
import java.util.UUID;
import java.util.Vector;

@Data
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class GameMessage {
    @NonNull
    private final UUID messageId;

    @Builder.Default
    @NonNull
    private final List<String> queriedBy = new Vector<>();

    @NonNull
    private final String senderId;

    @NonNull
    private final String senderName;

    @NonNull
    private final String message;

    @NonNull
    private final Long createdAt;

    public void addQueriedBy(String characterId) {
        if (queriedBy.contains(characterId)) {
            return;
        }
        queriedBy.add(characterId);
    }
}
