package skyxplore.exception;

import skyxplore.exception.base.ForbiddenException;

public class NotEnoughMaterialsException extends ForbiddenException {
    public NotEnoughMaterialsException(String message) {
        super(message);
    }
}
