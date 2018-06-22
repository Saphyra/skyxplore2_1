package skyxplore.exception;

import skyxplore.exception.base.UnauthorizedException;

public class InvalidAccessException extends UnauthorizedException {
    public InvalidAccessException(String message){
        super(message);
    }
}
