package skyxplore.exception;

import com.github.saphyra.exceptionhandling.exception.ConflictException;

public class EmailAlreadyExistsException extends ConflictException {
    public EmailAlreadyExistsException(String message) {
        super(message);
    }
}
