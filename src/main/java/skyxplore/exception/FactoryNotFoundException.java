package skyxplore.exception;

import com.github.saphyra.exceptionhandling.exception.NotFoundException;

public class FactoryNotFoundException extends NotFoundException {
    public FactoryNotFoundException(String message) {
        super(message);
    }
}
