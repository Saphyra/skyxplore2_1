package skyxplore.exception;

import skyxplore.controller.request.AllowBlockedCharacterRequest;
import skyxplore.exception.base.NotFoundException;

public class BlockedCharacterNotFoundException extends NotFoundException {
    public BlockedCharacterNotFoundException(AllowBlockedCharacterRequest request) {
        super("Blocked character not found with characterId "
            + request.getCharacterId()
            + " and blockedCharacterId "
            + request.getBlockedCharacterId());
    }
}
