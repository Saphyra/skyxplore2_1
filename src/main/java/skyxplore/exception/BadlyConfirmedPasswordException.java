package skyxplore.exception;

import skyxplore.exception.base.ForbiddenException;

public class BadlyConfirmedPasswordException extends ForbiddenException {
    public BadlyConfirmedPasswordException(String message) {
        super(message);
    }
}
