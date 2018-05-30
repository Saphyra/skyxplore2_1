package skyxplore.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.TimeZone;

@Component
@Slf4j
public class ExpirationDateResolver {
    public Calendar getExpirationDate(){
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        calendar.add(Calendar.MINUTE, -15);
        return calendar;
    }
}
