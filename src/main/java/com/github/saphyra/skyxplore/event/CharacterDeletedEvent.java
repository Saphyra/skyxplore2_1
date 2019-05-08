package com.github.saphyra.skyxplore.event;

import lombok.Data;

@Data
public class CharacterDeletedEvent {
    private final String characterId;
}
