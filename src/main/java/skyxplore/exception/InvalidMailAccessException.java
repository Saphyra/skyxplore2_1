package skyxplore.exception;

import skyxplore.exception.base.UnauthorizedException;

public class InvalidMailAccessException extends UnauthorizedException {
    public InvalidMailAccessException(String message) {
        super(message);
    }
}
