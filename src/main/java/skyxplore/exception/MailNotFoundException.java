package skyxplore.exception;

import skyxplore.exception.base.NotFoundException;

public class MailNotFoundException extends NotFoundException {
    public MailNotFoundException(String message) {
        super(message);
    }
}
