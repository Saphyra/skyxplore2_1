package skyxplore.exception;

import com.github.saphyra.exceptionhandling.exception.ForbiddenException;

public class BadlyConfirmedPasswordException extends ForbiddenException {
    public BadlyConfirmedPasswordException(String message) {
        super(message);
    }
}
