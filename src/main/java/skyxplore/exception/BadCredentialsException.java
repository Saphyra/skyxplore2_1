package skyxplore.exception;

import skyxplore.exception.base.UnauthorizedException;

public class BadCredentialsException extends UnauthorizedException {
    public BadCredentialsException(String message){
        super(message);
    }
}
