package selenium.util;

import java.util.UUID;

public class UserUtil {
    public static String randomUID(){
        return UUID.randomUUID().toString();
    }
}
