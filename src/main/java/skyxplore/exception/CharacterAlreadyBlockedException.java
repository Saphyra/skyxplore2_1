package skyxplore.exception;

import skyxplore.exception.base.ConflictException;

public class CharacterAlreadyBlockedException extends ConflictException {
    public CharacterAlreadyBlockedException(String blockedCharacterId, String characterId) {
        super(characterId + " is already blocked " + blockedCharacterId);
    }
}
