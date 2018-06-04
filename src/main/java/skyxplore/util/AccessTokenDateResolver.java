package skyxplore.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.TimeZone;

@Component
@Slf4j
public class AccessTokenDateResolver {
    public static final Integer EXPIRATION_TIME_IN_MINUTES = 15;

    public Calendar getExpirationDate(){
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        calendar.add(Calendar.MINUTE, -EXPIRATION_TIME_IN_MINUTES);
        return calendar;
    }

    public Calendar getActualDate(){
        return Calendar.getInstance(TimeZone.getTimeZone("UTC"));
    }
}
