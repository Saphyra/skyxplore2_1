package com.github.saphyra.skyxplore.lobby.lobby.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

@Data
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
//TODO unit test
public class LobbyEvent {
    @NonNull
    private final LobbyEventType eventType;

    private final Object data;

    @NonNull
    @Builder.Default
    private final List<String> queriedBy = new Vector<>();

    public void addQueriedBy(String characterId) {
        queriedBy.add(characterId);
    }

    public List<String> getQueriedBy() {
        return new ArrayList<>(queriedBy);
    }
}
