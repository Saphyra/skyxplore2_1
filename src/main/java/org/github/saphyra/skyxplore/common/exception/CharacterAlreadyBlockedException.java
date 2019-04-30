package org.github.saphyra.skyxplore.common.exception;

import com.github.saphyra.exceptionhandling.exception.ConflictException;

public class CharacterAlreadyBlockedException extends ConflictException {
    public CharacterAlreadyBlockedException(String blockedCharacterId, String characterId) {
        super(characterId + " is already blocked " + blockedCharacterId);
    }
}
