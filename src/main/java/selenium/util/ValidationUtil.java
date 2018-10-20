package selenium.util;

import java.util.Optional;

import static org.junit.Assert.assertTrue;

public class ValidationUtil {
    public static <T> Optional<T> validateIfPresent(Optional<T> optional) {
        assertTrue(optional.isPresent());
        return optional;
    }
}
