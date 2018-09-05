package skyxplore.exception;

import skyxplore.exception.base.NotFoundException;

public class BlockedCharacterNotFoundException extends NotFoundException {
    public BlockedCharacterNotFoundException(String characterId, String blockedCharacterId) {
        super("Blocked character not found with characterId "
            + characterId
            + " and blockedCharacterId "
            + blockedCharacterId
        );
    }
}
