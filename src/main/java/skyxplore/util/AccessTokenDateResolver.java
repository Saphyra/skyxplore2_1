package skyxplore.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Component
@Slf4j
//TODO unit test
public class AccessTokenDateResolver {
    private static final Integer EXPIRATION_TIME_IN_MINUTES = 15;

    public LocalDateTime getExpirationDate(){
        LocalDateTime expirationDate = LocalDateTime.now(ZoneOffset.UTC);
        return expirationDate.minusMinutes(EXPIRATION_TIME_IN_MINUTES);
    }

    public LocalDateTime getActualDate(){
        return LocalDateTime.now(ZoneOffset.UTC);
    }
}
