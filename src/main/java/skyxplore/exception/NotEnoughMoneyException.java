package skyxplore.exception;

import skyxplore.exception.base.ForbiddenException;

public class NotEnoughMoneyException extends ForbiddenException {
    public NotEnoughMoneyException(String message){
        super(message);
    }
}
