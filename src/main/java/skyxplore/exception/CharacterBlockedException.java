package skyxplore.exception;

import skyxplore.exception.base.ForbiddenException;

public class CharacterBlockedException extends ForbiddenException {
    public CharacterBlockedException(String message) {
        super(message);
    }
}
