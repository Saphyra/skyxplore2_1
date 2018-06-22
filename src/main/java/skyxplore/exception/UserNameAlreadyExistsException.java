package skyxplore.exception;

import skyxplore.exception.base.ConflictException;

public class UserNameAlreadyExistsException extends ConflictException {
    public UserNameAlreadyExistsException(String message) {
        super(message);
    }
}
