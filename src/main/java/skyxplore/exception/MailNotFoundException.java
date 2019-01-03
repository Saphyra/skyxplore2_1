package skyxplore.exception;

import com.github.saphyra.exceptionhandling.exception.NotFoundException;

public class MailNotFoundException extends NotFoundException {
    public MailNotFoundException(String message) {
        super(message);
    }
}
