package skyxplore.auth.domain.exception;

public class AccessTokenExpiredException extends RuntimeException {
    public AccessTokenExpiredException(String message){
        super(message);
    }
}
