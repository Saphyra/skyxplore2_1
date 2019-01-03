package skyxplore.exception;

import com.github.saphyra.exceptionhandling.exception.UnauthorizedException;

public class AccessTokenExpiredException extends UnauthorizedException {
    public AccessTokenExpiredException(String message){
        super(message);
    }
}
