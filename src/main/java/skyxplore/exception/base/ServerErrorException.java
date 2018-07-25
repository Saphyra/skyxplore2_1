package skyxplore.exception.base;

import org.springframework.http.HttpStatus;

public class ServerErrorException extends SkyXpException {
    public ServerErrorException(String message) {
        super(HttpStatus.INTERNAL_SERVER_ERROR, message);
    }
}
