package selenium.logic.util;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Supplier;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class WaitUtil {
    public static void waitUntil(Supplier<Boolean> booleanSupplier, String logMessage) {
        log.info(logMessage);
        int counter = 0;
        boolean result;
        do {
            log.debug("Wait attempt: {}", counter);
            result = booleanSupplier.get();
            counter++;
            if (counter >= 20) {
                throw new RuntimeException("Condition failed after 100 attempts");
            }
            sleep(100);
        } while (!result);
    }

    public static <T> Optional<T> getWithWait(Supplier<Optional<T>> supplier, String logMessage) {
        log.info(logMessage);
        int counter = 0;
        Optional<T> result;
        do {
            log.debug("Query attempts: {}", counter);
            result = supplier.get();
            sleep(100);
            counter++;
            if (counter >= 20) {
                return Optional.empty();
            }
        } while (!result.isPresent());
        return result;
    }

    public static <T> List<T> getListWithWait(Supplier<List<T>> supplier, String logMessage) {
        log.info(logMessage);
        int counter = 0;
        List<T> result;
        do {
            log.debug("Query attempts: {}", counter);
            result = supplier.get();
            Objects.requireNonNull(result);
            sleep(100);
            counter++;
            if (counter >= 20) {
                return Collections.emptyList();
            }
        } while (result.isEmpty());
        return result;
    }

    public static void sleep(long timeout) {
        try {
            Thread.sleep(timeout);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
