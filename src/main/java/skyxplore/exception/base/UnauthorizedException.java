package skyxplore.exception.base;

import org.springframework.http.HttpStatus;

public class UnauthorizedException extends SkyXpException {
    public UnauthorizedException(String message) {
        super(HttpStatus.UNAUTHORIZED, message);
    }
}
