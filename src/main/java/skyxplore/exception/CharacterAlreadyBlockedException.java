package skyxplore.exception;

import skyxplore.controller.request.community.BlockCharacterRequest;
import skyxplore.exception.base.ConflictException;

public class CharacterAlreadyBlockedException extends ConflictException {
    public CharacterAlreadyBlockedException(BlockCharacterRequest request) {
        super(request.getCharacterId() + " is already blocked " + request.getBlockedCharacterId());
    }
}
