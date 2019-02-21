package selenium.logic.util;

import lombok.extern.slf4j.Slf4j;

import java.util.Optional;
import java.util.function.Supplier;

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
            if(counter >= 100){
                throw new RuntimeException("Condition failed after 100 attempts");
            }
            sleep(100);
        } while (!result);
    }

    public static <T> Optional<T> getWithWait(Supplier<Optional<T>> supplier) {
        return getWithWait(supplier, "Querying item...");
    }

    public static <T> Optional<T> getWithWait(Supplier<Optional<T>> supplier, String logMessage) {
        log.info(logMessage);
        int counter = 0;
        Optional<T> result;
        do {
            log.info("Query attempts: {}", counter);
            result = supplier.get();
            sleep(100);
            counter++;
            if(counter >= 100){
                return Optional.empty();
            }
        } while (!result.isPresent());
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
