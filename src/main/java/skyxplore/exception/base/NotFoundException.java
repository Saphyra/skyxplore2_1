package skyxplore.exception.base;

import org.springframework.http.HttpStatus;

public class NotFoundException extends SkyXpException {
    public NotFoundException(String message) {
        super(HttpStatus.NOT_FOUND, message);
    }
}
