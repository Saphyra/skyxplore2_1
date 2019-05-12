package com.github.saphyra.skyxplore.lobby.message.domain;

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
public class Message {
    @NonNull
    private final UUID messageId;

    @NonNull
    private final UUID lobbyId;

    @Builder.Default
    @NonNull
    private final List<String> queriedBy = new Vector<>();

    @NonNull
    private final String sender;

    @NonNull
    private final String message;

    @NonNull
    private final Long createdAt;

    public void addQueriedBy(String characterId) {
        queriedBy.add(characterId);
    }
}
