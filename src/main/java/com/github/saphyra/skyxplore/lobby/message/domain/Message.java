package com.github.saphyra.skyxplore.lobby.message.domain;

import java.util.List;
import java.util.UUID;
import java.util.Vector;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

@Data
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Message {
    @NonNull
    private final UUID messageId;

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
        if (queriedBy.contains(characterId)) {
            return;
        }
        queriedBy.add(characterId);
    }
}
