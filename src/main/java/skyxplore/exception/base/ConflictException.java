package skyxplore.exception.base;

import org.springframework.http.HttpStatus;

public class ConflictException extends SkyXpException {
    public ConflictException(String message){
        super(HttpStatus.CONFLICT, message);
    }
}
