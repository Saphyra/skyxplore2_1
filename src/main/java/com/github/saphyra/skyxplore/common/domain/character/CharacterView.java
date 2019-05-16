package com.github.saphyra.skyxplore.common.domain.character;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

@Data
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class CharacterView {
    @NonNull
    private final String characterId;

    @NonNull
    private final String characterName;
}
