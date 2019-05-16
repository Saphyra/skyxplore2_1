package com.github.saphyra.skyxplore.community.blockedcharacter.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

@Data
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class BlockedCharacter {
    private final Long blockedCharacterEntityId;

    @NonNull
    private final String characterId;

    @NonNull
    private final String blockedCharacterId;

    public BlockedCharacter(String characterId, String blockedCharacterId) {
        this.blockedCharacterEntityId = null;
        this.characterId = characterId;
        this.blockedCharacterId = blockedCharacterId;
    }
}
