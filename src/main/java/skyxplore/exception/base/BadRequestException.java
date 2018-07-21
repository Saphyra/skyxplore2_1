package skyxplore.exception.base;

import org.springframework.http.HttpStatus;

public class BadRequestException extends SkyXpException {
    public BadRequestException(String message) {
        super(HttpStatus.BAD_REQUEST, message);
    }
}
