package skyxplore.auth.domain.exception;

public class BadRequestAuthException extends RuntimeException {
    public BadRequestAuthException(String message){
        super(message);
    }
}
