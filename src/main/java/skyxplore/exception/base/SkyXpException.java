package skyxplore.exception.base;

import lombok.Data;
import org.springframework.http.HttpStatus;

@SuppressWarnings({"WeakerAccess", "Lombok"})
@Data
public abstract class SkyXpException extends RuntimeException {
    private final HttpStatus responseStatus;

    protected SkyXpException(HttpStatus responseStatus, String message){
        super(message);
        this.responseStatus = responseStatus;
    }
}
