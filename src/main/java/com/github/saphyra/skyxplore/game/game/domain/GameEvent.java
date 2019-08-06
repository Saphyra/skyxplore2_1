package com.github.saphyra.skyxplore.game.game.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

@Data
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class GameEvent {
    @NonNull
    private final GameEventType eventType;

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
