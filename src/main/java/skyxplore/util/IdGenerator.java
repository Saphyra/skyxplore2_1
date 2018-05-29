package skyxplore.util;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class IdGenerator {
    public String getRandomId(){
        return UUID.randomUUID().toString();
    }
}
