package skyxplore.exception.base;

import org.springframework.http.HttpStatus;

public class ForbiddenException extends SkyXpException {
    public ForbiddenException(String message) {
        super(HttpStatus.FORBIDDEN, message);
    }
}
