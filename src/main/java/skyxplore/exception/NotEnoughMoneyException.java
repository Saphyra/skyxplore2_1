package skyxplore.exception;

import com.github.saphyra.exceptionhandling.exception.ForbiddenException;

public class NotEnoughMoneyException extends ForbiddenException {
    public NotEnoughMoneyException(String message) {
        super(message);
    }
}
