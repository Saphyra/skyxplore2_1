package skyxplore.exception;

import skyxplore.exception.base.NotFoundException;

public class FactoryNotFoundException extends NotFoundException {
    public FactoryNotFoundException(String message) {
        super(message);
    }
}
